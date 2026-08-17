package com.example.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.example.data.repository.song.SongRepository
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.ArrayDeque
import javax.inject.Inject

@SuppressLint("Instantiatable")
@AndroidEntryPoint
class MusicService : MediaSessionService() {
    @Inject
    lateinit var songRepository: SongRepository

    @Inject
    lateinit var player: ExoPlayer

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val pendingSongIds = ArrayDeque<Long>()
    private var queueLoadJob: Job? = null
    private var preloadJob: Job? = null
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player)
            .setCallback(sessionCallback)
            .build()
        player.addListener(playerListener)
    }

    override fun onGetSession(controllerInfo: ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        serviceScope.cancel()
        player.removeListener(playerListener)
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    private val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            preloadUpcomingItems()
        }
    }

    private val sessionCallback = object : MediaSession.Callback {
        @OptIn(UnstableApi::class)
        override fun onConnect(session: MediaSession, controller: ControllerInfo): ConnectionResult {
            val sessionCommands = ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                .add(SessionCommand(COMMAND_ADD_TO_NEXT, Bundle.EMPTY))
                .add(SessionCommand(COMMAND_CLEAR, Bundle.EMPTY))
                .add(SessionCommand(COMMAND_ADD_TO_QUEUE_MULTIPLE, Bundle.EMPTY))
                .build()

            return ConnectionResult.AcceptedResultBuilder(session)
                .setAvailableSessionCommands(sessionCommands)
                .build()
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
            when (customCommand.customAction) {
                COMMAND_ADD_TO_NEXT -> handleAddToNext(args)
                COMMAND_CLEAR -> clear()
                COMMAND_ADD_TO_QUEUE_MULTIPLE -> handleAddToQueueMultiple(args)
                else -> return Futures.immediateFuture(SessionResult(SessionResult.RESULT_ERROR_BAD_VALUE))
            }

            return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
        }
    }

    private fun handleAddToNext(args: Bundle) {
        val currentIndex = player.currentMediaItemIndex
        val songId = args.getLong(EXTRA_SONG_ID, -1L)
        if (songId == -1L) return

        serviceScope.launch {
            val mediaItem = buildSingleItem(songId) ?: return@launch
            val insertIndex = if (currentIndex == C.INDEX_UNSET) 0 else currentIndex + 1
            player.addMediaItem(insertIndex, mediaItem)
        }
    }

    private fun handleAddToQueueMultiple(args: Bundle) {
        val songIdsList = args.getLongArray(EXTRA_SONG_IDS)?.toList()
        val startIndex = args.getInt(EXTRA_START_INDEX, 0)
        if (songIdsList.isNullOrEmpty() || startIndex !in songIdsList.indices) return

        queueLoadJob?.cancel()
        preloadJob?.cancel()
        pendingSongIds.clear()
        queueLoadJob = serviceScope.launch {
            val clickedSongId = songIdsList[startIndex]
            val clickedMediaItem = buildSingleItem(clickedSongId)
            if (clickedMediaItem == null) {
                Log.e("MusicService", "Failed to get URL for the clicked song.")
                return@launch
            }

            player.setMediaItems(listOf(clickedMediaItem), 0, 0L)
            player.prepare()
            player.play()

            val itemsAfterClicked = songIdsList.subList(startIndex + 1, songIdsList.size)
            val itemsBeforeClicked = songIdsList.subList(0, startIndex)
            pendingSongIds.addAll(itemsAfterClicked)
            pendingSongIds.addAll(itemsBeforeClicked)
            preloadUpcomingItems()
        }
    }

    private fun clear() {
        serviceScope.launch {
            queueLoadJob?.cancel()
            preloadJob?.cancel()
            pendingSongIds.clear()
            player.clearMediaItems()
            player.stop()
        }
    }

    private fun preloadUpcomingItems() {
        if (preloadJob?.isActive == true || pendingSongIds.isEmpty()) return

        val currentIndex = player.currentMediaItemIndex.takeUnless { it == C.INDEX_UNSET } ?: 0
        val availableAhead = (player.mediaItemCount - currentIndex - 1).coerceAtLeast(0)
        if (availableAhead > PRELOAD_LOW_WATER_MARK) return

        val idsToLoad = buildList {
            repeat(minOf(PRELOAD_TARGET - availableAhead, pendingSongIds.size)) {
                add(pendingSongIds.removeFirst())
            }
        }
        if (idsToLoad.isEmpty()) return

        preloadJob = serviceScope.launch {
            try {
                val mediaItems = buildItems(idsToLoad)
                if (mediaItems.isNotEmpty()) player.addMediaItems(mediaItems)
            } finally {
                preloadJob = null
                preloadUpcomingItems()
            }
        }
    }

    private suspend fun buildSingleItem(songId: Long): MediaItem? {
        return buildItems(listOf(songId)).firstOrNull()
    }

    private suspend fun buildItems(songIds: List<Long>): List<MediaItem> {
        return try {
            val urlResult = songRepository.getSongUrl(songIds)
            val urlsById = urlResult.getOrNull()?.data?.associateBy { it.id }.orEmpty()
            if (urlResult.isFailure) {
                Log.e("MusicService", "getSongUrl failed: ${urlResult.exceptionOrNull()?.message}")
            }
            songIds.mapNotNull { songId ->
                urlsById[songId]?.takeIf { it.url.isNotEmpty() }?.let { songUrlData ->
                    MediaItem.Builder()
                        .setMediaId(songUrlData.id.toString())
                        .setUri(songUrlData.url)
                        .build()
                }
            }
        } catch (e: Exception) {
            Log.e("MusicService", "Error fetching URLs for ${songIds.size} songs", e)
            emptyList()
        }
    }

    private companion object {
        const val PRELOAD_TARGET = 5
        const val PRELOAD_LOW_WATER_MARK = 2
    }
}
