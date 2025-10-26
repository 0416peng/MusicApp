package com.example.artist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.HotSong

@Composable
fun ArtistScreen(viewModel: ArtistViewModel = hiltViewModel()) {
    // 1. 订阅统一的UI状态
    val uiState by viewModel.uiState.collectAsState()

    // 2. 根据不同的状态，显示不同的UI
    when (val state = uiState) {
        is ArtistUiState.Loading -> {
            // 加载中状态：显示一个居中的加载指示器
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ArtistUiState.Success -> {
            // 成功状态：将数据传递给专门负责展示内容的 Composable
            val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
            ArtistDetailContent(
                detail = state.detail,
                hotSongs = state.hotSongs,
                currentlyPlayingSongId = currentlyPlayingSongId,
                onPlayPauseClicked = { songId->
                    viewModel.onPlayPauseClicked(songId)
                }
            )
        }
        is ArtistUiState.Error -> {
            // 错误状态：显示一个居中的错误信息
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "加载失败: ${state.message}")
            }
        }
    }
}

/**
 * 专门负责显示艺术家详情页“成功”状态下内容的 Composable
 */
@Composable
fun ArtistDetailContent(
    detail: ArtistDetail?,
    hotSongs: ArtistHotSongs?,
    currentlyPlayingSongId: Long?,
    onPlayPauseClicked: (id: Long) -> Unit, // 传递播放事件
    modifier: Modifier = Modifier
) {

    // 3. 将之前的所有UI代码移动到这里
    LazyColumn(modifier = modifier.fillMaxSize()) {

        // 检查detail是否为空，避免空指针异常
        detail?.let {
            item {
                AsyncImage(
                    model = it.data.artist.cover,
                    contentDescription = "歌手封面",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 16.dp, bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            it.data.artist.name,
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        if (!it.data.artist.transNames.isNullOrEmpty()) {
                            val translations = it.data.artist.transNames
                            Text(
                                translations!!.joinToString("/"),
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /*TODO:实现播放全部的逻辑*/ }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "播放热门50",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("播放热门 50", fontSize = 16.sp)
            }
        }

        // 检查hotSongs是否为空
        hotSongs?.let {
            itemsIndexed(it.songs) { index, item ->

                SongRowItem(
                    song = item,
                    onPlayClick = { songId -> onPlayPauseClicked(songId) },
                    currentlyPlayingSongId = currentlyPlayingSongId,
                    index = index
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /*TODO:查看全部歌曲*/ }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("查看全部   》", color = Color.Gray)
            }
        }
    }
}

@Composable
fun SongRowItem(song: HotSong, onPlayClick: (id: Long) -> Unit, currentlyPlayingSongId: Long?, index: Int) {
    // ... 此函数保持不变 ...
    val isPlaying = currentlyPlayingSongId == song.id
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlayClick(song.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.padding(end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isPlaying) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "正在播放",
                    tint = Color.Red
                )
            } else {
                Text(
                    text = "${index + 1}",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }


        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            val color = if (isPlaying) Color.Red else Color.Black
            Text(
                text = song.name,
                fontSize = 16.sp,
                color = color,
                maxLines = 1
            )
            Text(
                text = song.ar.joinToString("/") { it.name },
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1
            )
        }

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "更多选项",
            tint = Color.Gray,
            modifier = Modifier.clickable { /*TODO:歌曲菜单*/ }
        )
    }
}