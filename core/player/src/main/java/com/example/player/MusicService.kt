package com.example.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
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
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private var backgroundLoadJob: Job? = null
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player)
            .setCallback(sessionCallback)
            .build()
    }

    override fun onGetSession(controllerInfo: ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        serviceScope.cancel()
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
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

        serviceScope.launch {
            val clickedSongId = songIdsList[startIndex]
            val clickedMediaItem = buildSingleItem(clickedSongId)
            if (clickedMediaItem == null) {
                Log.e("MusicService", "Failed to get URL for the clicked song.")
                return@launch
            }

            player.setMediaItems(listOf(clickedMediaItem), 0, 0L)
            player.prepare()
            player.play()
            backgroundLoadJob?.cancel()

            val itemsAfterClicked = songIdsList.subList(startIndex + 1, songIdsList.size)
            val itemsBeforeClicked = songIdsList.subList(0, startIndex)
            backgroundLoadJob = addRemainingItemsInOrder(itemsAfterClicked, itemsBeforeClicked)
        }
    }

    private fun clear() {
        serviceScope.launch {
            backgroundLoadJob?.cancel()
            player.clearMediaItems()
            player.stop()
        }
    }

    private fun addRemainingItemsInOrder(itemsAfter: List<Long>, itemsBefore: List<Long>): Job {
        return serviceScope.launch(Dispatchers.IO) {
            for (songId in itemsAfter) {
                ensureActive()
                val mediaItem = buildSingleItem(songId)
                if (mediaItem != null) {
                    withContext(Dispatchers.Main) {
                        player.addMediaItem(mediaItem)
                    }
                }
            }

            for (songId in itemsBefore) {
                ensureActive()
                val mediaItem = buildSingleItem(songId)
                if (mediaItem != null) {
                    withContext(Dispatchers.Main) {
                        player.addMediaItem(mediaItem)
                    }
                }
            }
        }
    }

    private suspend fun buildSingleItem(songId: Long): MediaItem? {
        return try {
            val urlResult = songRepository.getSongUrl(listOf(songId))
            val songUrlData = urlResult.getOrNull()?.data?.firstOrNull()
            if (songUrlData != null) {
                MediaItem.Builder()
                    .setMediaId(songUrlData.id.toString())
                    .setUri(songUrlData.url)
                    .build()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("MusicService", "Error fetching URL for songId: $songId", e)
            null
        }
    }
}
