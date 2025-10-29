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
import hilt_aggregated_deps._com_example_player_MusicService_GeneratedInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject



const val ACTION_PLAY_OR_PAUSE = "ACTION_PLAY_OR_PAUSE" // 用于播放/暂停当前项
const val ACTION_ADD_TO_QUEUE = "ACTION_ADD_TO_QUEUE"     // 仅添加到队尾，不立即播放
const val ACTION_ADD_TO_NEXT="ACTION_ADD_TO_NEXT"       //添加到下一首
const val ACTION_CLEAR="ACTION_CLEAR"     //清除列表
const val ACTION_SKIP_TO_ITEM="ACTION_SKIP_TO_ITEM"//跳转到列表的其他歌曲
const val ACTION_ADD_TO_QUEUE_MULTIPLE="ACTION_ADD_TO_QUEUE_MULTIPLE"


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
        when(intent?.action){
            ACTION_ADD_TO_QUEUE->handleAddToQueue(intent)
            ACTION_PLAY_OR_PAUSE->handlePlayOrPause(intent)
            ACTION_ADD_TO_NEXT->handleAddToNext(intent)
            ACTION_CLEAR->clear()
            ACTION_SKIP_TO_ITEM->handleSkipToItem(intent)
            ACTION_ADD_TO_QUEUE_MULTIPLE->handleAddToQueueMultiple(intent)

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
    }//继续播放或者暂停播放
    private fun handleAddToQueue(intent: Intent){
        val songId=intent.getLongExtra("songId",-1L)
        if(songId!=-1L){
            serviceScope.launch {
                val songIds=listOf(songId)
                val mediaItems=buildItem(songIds)
                if (mediaItems!=null){
                    player.addMediaItem(mediaItems[0])
                }
                if(!player.isPlaying&&player.playbackState== ExoPlayer.STATE_IDLE){
                    player.prepare()
                    player.play()
                }
            }
        }
    }//添加单首到队尾，如果没有歌曲正在播放则进行播放。
    private fun handleAddToNext(intent: Intent){
        val currentIndex=player.currentMediaItemIndex
        val songId=intent.getLongExtra("songId",-1L)
        if(songId!=-1L){
            serviceScope.launch {
                val songIds=listOf(songId)
                val mediaItems=buildItem(songIds)
                if(mediaItems!=null){
                    player.addMediaItem(currentIndex+1,mediaItems[0])
                }
            }
        }
    }//将单首歌曲添加到下一首
    private fun handleAddToQueueMultiple(intent: Intent){

        val songIds=intent.getLongArrayExtra("songIds")
        val startIndex=intent.getIntExtra("startIndex",0)
        val songIdsList=songIds?.toList()
        if(!songIdsList.isNullOrEmpty()){
            serviceScope.launch {
                val mediaItems=buildItem(songIdsList)
                if(mediaItems!=null&&mediaItems.isNotEmpty()){
                    val validStartIndex = if (startIndex in mediaItems.indices) startIndex else 0
                    player.setMediaItems(mediaItems,validStartIndex,0L)
                        player.prepare()
                        player.play()
                }
            }
        }
    }//清除当前正在播放的列表并添加新的列表。
    private fun handleSkipToItem(intent: Intent){
        val itemIndex=intent.getIntExtra("itemIndex",-1)
        if(itemIndex!=-1&&itemIndex< player.mediaItemCount){
            player.seekTo(itemIndex,0L)
            player.play()
        }else{
            Log.w("MusicService","Invalid item index: $itemIndex")
        }
    }//播放列表中的其他歌曲
    private fun clear(){
        serviceScope.launch {
            player.clearMediaItems()
            player.stop()
        }
    }//清除列表并关闭播放器

    private suspend fun buildItem(songId:List<Long>):List<MediaItem>?{
        val urlResult=songRepository.getSongUrl(songId)
        return urlResult.getOrNull()?.data?.map { urlData ->
            val songId=urlData.id
            val url=urlData.url
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
    }//创建buildItem
}