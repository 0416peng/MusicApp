package com.example.player

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import com.example.player.MusicPlayerManager
import com.example.data.repository.song.SongRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject



const val ACTION_PLAY_OR_PAUSE = "ACTION_PLAY_OR_PAUSE" // 用于播放/暂停当前项
const val ACTION_ADD_AND_PLAY = "ACTION_ADD_AND_PLAY"   // 用于添加并立即播放
const val ACTION_ADD_TO_QUEUE = "ACTION_ADD_TO_QUEUE"     // 仅添加到队尾，不立即播放
const val ACTION_ADD_TO_NEXT="ACTION_ADD_TO_NEXT"       //添加到下一首
const val ACTION_CLEAR="ACTION_CLEAR"        //清除列表

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
       /* val songId=intent?.getLongExtra("songId",-1L)?:-1L
        if(songId!=-1L){
           serviceScope.launch {
               val urlResult=songRepository.getSongUrl(songId)
               urlResult.onSuccess {
                   result->
                   val mediaItem= MediaItem.Builder()
                       .setMediaId("mediaID")
                       .setUri(result.data[0].url)
                       .setMediaMetadata(
                           MediaMetadata.Builder()
                               .setTitle("title")
                               .setArtist("artist")
                               .build()
                       )
                       .build()
                   player.setMediaItem(mediaItem)
                   player.prepare()
                   player.play()
               }.onFailure { error->
                   musicPlayerManager.onPlayerPaused()
                   Log.d("playError",error.toString())
               }
           }
        }else{
            player.pause()
        }
        return START_NOT_STICKY*/
        when(intent?.action){
            ACTION_ADD_TO_QUEUE->handleAddToQueue(intent)
            ACTION_PLAY_OR_PAUSE->handlePlayOrPause(intent)
        else->{
            if(player.isPlaying){
            player.pause()}
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
    }
    private fun handlePlayOrPause(intent: Intent){
        if(player.isPlaying) player.pause() else player.play()
    }
    private fun handleAddToQueue(intent: Intent){
        val songId=intent.getLongExtra("songId",-1L)
        if(songId!=-1L){
            serviceScope.launch {
                val mediaItem=buildItem(songId)
                if (mediaItem!=null){
                    player.addMediaItem(mediaItem)
                }
                if(!player.isPlaying&&player.playbackState== ExoPlayer.STATE_IDLE){
                    player.prepare()
                    player.play()
                }
            }
        }
    }
    private suspend fun buildItem(songId: Long): MediaItem?{
        val urlResult=songRepository.getSongUrl(songId)

        return urlResult.getOrNull()?.data?.firstOrNull()?.url?.let { url ->
            MediaItem.Builder()
                .setMediaId(songId.toString())
                .setUri(url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("歌曲名 $songId") // TODO: 替换为真实歌曲名
                        .setArtist("歌手")         // TODO: 替换为真实歌手名
                        .build()
                )
                .build()
        }
    }
}