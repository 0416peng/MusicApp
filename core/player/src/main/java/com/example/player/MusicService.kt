package com.example.player

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import androidx.media3.common.C
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.ensureActive
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
    private var backgroundLoadJob: Job?=null
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
            ACTION_PLAY_OR_PAUSE->handlePlayOrPause(intent)
            ACTION_ADD_TO_NEXT->handleAddToNext(intent)
            ACTION_CLEAR->clear()
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
    }

    private fun handleAddToNext(intent: Intent){
        val currentIndex = player.currentMediaItemIndex
        val songId = intent.getLongExtra("songId",-1L)
        if(songId != -1L){
            serviceScope.launch {
                val mediaItem = buildSingleItem(songId)
                if(mediaItem != null){
                    val insertIndex = if (currentIndex == C.INDEX_UNSET) 0 else currentIndex + 1
                    player.addMediaItem(insertIndex, mediaItem)
                }
            }
        }
    }//将单首歌曲添加到下一首
    private fun handleAddToQueueMultiple(intent: Intent) {
        val songIds = intent.getLongArrayExtra("songIds")
        val startIndex = intent.getIntExtra("startIndex", 0)
        val songIdsList = songIds?.toList()
        if (!songIdsList.isNullOrEmpty() && startIndex in songIdsList.indices) {
            serviceScope.launch {
                val clickedSongId = songIdsList[startIndex]
                val clickedMediaItem = buildSingleItem(clickedSongId)
                if (clickedMediaItem != null) {
                    player.setMediaItems(listOf(clickedMediaItem), 0, 0L)
                    player.prepare()
                    player.play()
                    Log.d("MusicService", "Started immediate playback for songId: $clickedSongId")
                    backgroundLoadJob?.cancel()
                    val itemsAfterClicked = songIdsList.subList(startIndex + 1, songIdsList.size)
                    val itemsBeforeClicked = songIdsList.subList(0, startIndex)
                   backgroundLoadJob= addRemainingItemsInOrder(itemsAfterClicked, itemsBeforeClicked)
                } else {
                    Log.e("MusicService", "Failed to get URL for the clicked song, cannot start playback.")
                }
            }
        }
    }
    private fun clear(){
        serviceScope.launch {
            player.clearMediaItems()
            player.stop()
        }
    }//清除列表并关闭播放器

    private fun addRemainingItemsInOrder(itemsAfter: List<Long>, itemsBefore: List<Long>): Job {
       return serviceScope.launch(Dispatchers.IO) {
            for (songId in itemsAfter) {
                val mediaItem = buildSingleItem(songId)
                if (mediaItem != null) {
                    launch(Dispatchers.Main) {
                        player.addMediaItem(mediaItem)
                    }
                }
            }

            for (songId in itemsBefore) {
                ensureActive()
                val mediaItem = buildSingleItem(songId)
                if (mediaItem != null) {
                    launch(Dispatchers.Main) {
                        player.addMediaItem(mediaItem)
                    }
                }
            }

            Log.d("MusicService", "Finished adding remaining items in correct order.")
        }
    }//添加歌曲的逻辑
    private suspend fun buildSingleItem(songId: Long): MediaItem? {
        try {
            val urlResult = songRepository.getSongUrl(listOf(songId))
            val songUrlData = urlResult.getOrNull()?.data?.firstOrNull()
            if (songUrlData != null && songUrlData.url != null) {
                return MediaItem.Builder()
                    .setMediaId(songUrlData.id.toString())
                    .setUri(songUrlData.url)
                    .build()
            }
        } catch (e: Exception) {
            Log.e("MusicService", "Error fetching URL for songId: $songId", e)
        }
        return null
    }//获取单个歌曲的url
}