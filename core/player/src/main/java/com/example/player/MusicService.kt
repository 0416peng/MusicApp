package com.example.player

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
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
        player.addListener(object : Player.Listener{
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                if(mediaItem!=null){
                    val songId=mediaItem.mediaId.toLongOrNull()
                    if(songId!=null){
                        musicPlayerManager.onTrackChanged(songId)
                    }else{
                        musicPlayerManager.onPlayerStopped()
                    }
                }
            }
        })
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

    private suspend fun buildItem(songIds:List<Long>):List<MediaItem>?{
        val mediaItems = mutableListOf<MediaItem>()

        // 2. 遍历传入的每一个 songId
        val songId=songIds[0]
            try {
                // 3. 【核心改动】在循环内部，每次只请求一个 songId
                //    我们将 songId 包装成一个单元素的列表来调用现有 repository 方法
                val urlResult = songRepository.getSongUrl(listOf(songId))

                // 4. 从单次请求的结果中提取 URL
                val songUrlData = urlResult.getOrNull()?.data?.firstOrNull()

                if (songUrlData != null && songUrlData.url != null) {
                    // 5. 如果成功获取到有效的 URL，就创建并添加 MediaItem
                    val mediaItem = MediaItem.Builder()
                        .setMediaId(songUrlData.id.toString())
                        .setUri(songUrlData.url)
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setTitle("歌曲名 ${songUrlData.id}") // TODO: 替换为真实歌曲名
                                .setArtist("歌手")         // TODO: 替换为真实歌手名
                                .build()
                        )
                        .build()
                    mediaItems.add(mediaItem)
                } else {
                    // 如果某首歌的URL为null或请求失败，记录日志并跳过
                    Log.w("MusicService", "Failed to get URL for songId: $songId. Skipping.")
                }
            } catch (e: Exception) {
                // 捕获单次请求可能发生的异常（如网络问题），防止整个循环中断
                Log.e("MusicService", "Error fetching URL for songId: $songId", e)
            }


        // 6. 返回收集到的所有有效的 MediaItem
        return mediaItems
    }//创建buildItem
}