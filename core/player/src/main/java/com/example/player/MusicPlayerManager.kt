package com.example.player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionToken
import com.example.data.model.song.SongsListData
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val managerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val mainExecutor = ContextCompat.getMainExecutor(context)
    private val controllerFuture: ListenableFuture<MediaController>

    private var controller: MediaController? = null
    private var controllerListenerAttached = false
    private var progressJob: Job? = null

    private val _currentlyPlayingSongId = MutableStateFlow<Long?>(null)
    val currentlyPlayingSongId = _currentlyPlayingSongId.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _songsList = MutableStateFlow<List<SongsListData>?>(null)
    val songsList = _songsList.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0L)
    val playbackProgress = _playbackProgress.asStateFlow()

    private val _currentSongDuration = MutableStateFlow(0L)
    val currentSongDuration = _currentSongDuration.asStateFlow()

    init {
        val sessionToken = SessionToken(context, ComponentName(context, MusicService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                attachController(controllerFuture.get())
            },
            mainExecutor
        )
    }

    fun playOrPauseSong(songId: Long) {
        withController { mediaController ->
            if (mediaController.isPlaying) {
                mediaController.pause()
            } else {
                mediaController.play()
            }
        }

    }

    fun addMultipleToQueue(songs: List<SongsListData>?, startIndex: Int) {
        if (songs.isNullOrEmpty()) return
        _songsList.value = songs
        val songIds = songs.map { it.id }.toLongArray()
        sendCustomCommand(
            command = COMMAND_ADD_TO_QUEUE_MULTIPLE,
            args = Bundle().apply {
                putLongArray(EXTRA_SONG_IDS, songIds)
                putInt(EXTRA_START_INDEX, startIndex)
            }
        )
    }

    fun addToNext(songId: Long) {
        sendCustomCommand(
            command = COMMAND_ADD_TO_NEXT,
            args = Bundle().apply {
                putLong(EXTRA_SONG_ID, songId)
            }
        )
    }

    fun clear() {
        sendCustomCommand(COMMAND_CLEAR, Bundle.EMPTY)
        _currentlyPlayingSongId.value = null
        _playbackProgress.value = 0L
        _currentSongDuration.value = 0L
        _isPlaying.value = false
    }

    fun skipToItem(songIds: List<Long>, itemIndex: Int) {
        if (songIds.isEmpty() || itemIndex !in songIds.indices) return
        sendCustomCommand(
            command = COMMAND_ADD_TO_QUEUE_MULTIPLE,
            args = Bundle().apply {
                putLongArray(EXTRA_SONG_IDS, songIds.toLongArray())
                putInt(EXTRA_START_INDEX, itemIndex)
            }
        )
    }

    fun seekTo(position: Long) {
        withController { mediaController ->
            mediaController.seekTo(position)
            updateProgress(mediaController)
        }
    }

    private fun sendCustomCommand(command: String, args: Bundle) {
        withController { mediaController ->
            mediaController.sendCustomCommand(SessionCommand(command, Bundle.EMPTY), args)
        }
    }

    private fun withController(action: (MediaController) -> Unit) {
        controller?.let(action) ?: controllerFuture.addListener(
            {
                action(attachController(controllerFuture.get()))
            },
            mainExecutor
        )
    }

    private fun attachController(mediaController: MediaController): MediaController {
        controller = mediaController
        if (!controllerListenerAttached) {
            mediaController.addListener(playerListener)
            controllerListenerAttached = true
        }
        syncFromController(mediaController)
        return mediaController
    }

    private val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            _currentlyPlayingSongId.value = mediaItem?.mediaId?.toLongOrNull()
            controller?.let(::updateProgress)
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
            if (isPlaying) {
                startProgressUpdates()
            } else {
                stopProgressUpdates()
                controller?.let(::updateProgress)
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            controller?.let(::updateProgress)
            if (playbackState == Player.STATE_ENDED) {
                stopProgressUpdates()
            }
        }
    }

    private fun syncFromController(mediaController: MediaController) {
        _isPlaying.value = mediaController.isPlaying
        _currentlyPlayingSongId.value = mediaController.currentMediaItem?.mediaId?.toLongOrNull()
        updateProgress(mediaController)
        if (mediaController.isPlaying) startProgressUpdates()
    }

    private fun startProgressUpdates() {
        stopProgressUpdates()
        progressJob = managerScope.launch {
            while (true) {
                controller?.let(::updateProgress)
                delay(1000)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }

    private fun updateProgress(mediaController: MediaController) {
        _playbackProgress.value = mediaController.currentPosition.coerceAtLeast(0L)
        val duration = mediaController.duration
        if (duration != C.TIME_UNSET && duration > 0) {
            _currentSongDuration.value = duration
        }
    }
}
