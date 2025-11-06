package com.example.player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun PlayerScreen(
     viewModel: PlayViewModel= hiltViewModel(),
     id:Long
){
    LaunchedEffect(Unit) {
        viewModel.getSongLyric(id)
        viewModel.getSongDetail(id)
    }
    val songDetail by viewModel.songDetail.collectAsState()
    val lyricData by viewModel.lyricData.collectAsState()
    val infiniteTransition= rememberInfiniteTransition(label = "imageRotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 16000,
                easing = LinearEasing
            )
        )
    )
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        songDetail?.let {
            songDetail-> Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = songDetail.songs[0].al.picUrl,
                contentDescription = songDetail.songs[0].al.name,
                modifier = Modifier
                    .size(300.dp)
                    .clip(CircleShape)
                    .rotate(rotationAngle)
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 40.dp, start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = songDetail.songs[0].name,
                        fontSize = 18.sp,

                    )
                    Text(text = songDetail.songs[0].ar.joinToString("/") { it.name },
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                Text(text = "进度条占位")/*TODO:进度条*/
                Row(modifier = Modifier.fillMaxWidth().padding(top=12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    IconButton(onClick = { /* TODO: 切换循环模式 */ }) {
                        Icon(
                            imageVector = Icons.Default.Repeat,
                            contentDescription = "循环模式"
                        )
                    }

                    // 2. 上一首图标
                    IconButton(onClick = { /* TODO: 播放上一首 */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "上一首"
                        )
                    }

                    // 3. 暂停/播放图标 (可以根据播放状态切换)
                    IconButton(onClick = { /* TODO: 暂停或播放 */ }) {
                        Icon(
                            // 假设当前是播放状态，显示暂停图标
                            imageVector = Icons.Default.Pause,
                            contentDescription = "暂停/播放",
                            modifier = Modifier.size(48.dp) // 让中间的按钮更大更突出
                        )
                    }

                    // 4. 下一首图标
                    IconButton(onClick = { /* TODO: 播放下一首 */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "下一首"
                        )
                    }

                    // 5. 歌曲列表图标
                    IconButton(onClick = { /* TODO: 打开播放列表 */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.PlaylistPlay,
                            contentDescription = "播放列表"
                        )
                }
            }
        }

    }
}}