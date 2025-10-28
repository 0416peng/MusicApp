package com.example.player

import android.content.Intent
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.data.manager.MusicPlayerManager
import com.example.data.repository.song.SongRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : MediaSessionService() {
    @Inject
    lateinit var songRepository: SongRepository
    @Inject
    lateinit var player: ExoPlayer
    @Inject
    lateinit var musicPlayerManager: MusicPlayerManager
    private val serviceJob= SupervisorJob()
    private val serviceScope=CoroutineScope(Dispatchers.Main+serviceJob)
    private var mediaSession: MediaSession?=null
    override fun onCreate() {
        super.onCreate()
        mediaSession=MediaSession.Builder(this,player).build()
    }
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val songId=intent?.getLongExtra("songId",-1L)?:-1L
        if(songId!=-1L){
           serviceScope.launch {
               val urlResult=songRepository.getSongUrl(songId)
               urlResult.onSuccess {
                   url->
                   val mediaItem= MediaItem.fromUri(url.data.url)
                   player.setMediaItem(mediaItem)
                   player.prepare()
                   player.play()
               }.onFailure { error->
                   musicPlayerManager.onPlayerPaused()
                   Log.d("playError",error.toString())
               }
           }
        }
        return START_NOT_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
      serviceJob.cancel()
      mediaSession?.run { player.release()
      release()
          mediaSession=null
      }
    }}
