package com.example.player

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val _currentlyPlayingSongId = MutableStateFlow<Long?>(null)
    val currentlyPlayingSongId = _currentlyPlayingSongId.asStateFlow()
    fun onTrackChanged(songId: Long) {
        _currentlyPlayingSongId.value = songId
    }//修改当前正在播放的歌曲


    fun playOrPauseSong(songId: Long) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = ACTION_PLAY_OR_PAUSE
            putExtra("songId", songId)
        }
        context.startService(intent)
        onTrackChanged(songId)
    }//播放或者暂停歌曲

    fun addMultipleToQueue(songIds: List<Long>?, startIndex: Int) {
        if (songIds!!.isEmpty()) return
        val intent = Intent(context, MusicService::class.java).apply {
            action = ACTION_ADD_TO_QUEUE_MULTIPLE
            putExtra("songIds", songIds.toLongArray())
            putExtra("startIndex",startIndex)
        }
        context.startService(intent)
    }//添加队列

    fun addToNext(songId: Long) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = ACTION_ADD_TO_NEXT
            putExtra("songId", songId)
        }
        context.startService(intent)
    }//添加到下一首
    fun clear(){
        val intent=Intent(context,MusicService::class.java).apply {
            action= ACTION_CLEAR
        }
        context.startService(intent)
        }//清除歌单

    fun handleSkipToItem(itemIndex: Int) {
        val intent= Intent(context, MusicService::class.java).apply {
            action=ACTION_SKIP_TO_ITEM
            putExtra("itemIndex",itemIndex)
        }
        context.startService(intent)
    }//跳转到列表中的某一首
    fun onPlayerStopped() {
        _currentlyPlayingSongId.value = null
    }//播放器停止
    }
