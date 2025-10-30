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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.data.model.artist.HotSong
import com.example.ui.LoadingPlaceholder

@Composable
fun ArtistScreen(id: Long, viewModel: ArtistViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getArtistDetail(id)
        viewModel.getArtistHotSongs(id)
    }
    val detail by viewModel.detail.collectAsState()
    val hotSongs by viewModel.hotSongs.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()

    if (detail == null || hotSongs == null) {
        LoadingPlaceholder()
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {


            item {
                AsyncImage(
                    model = detail!!.data.artist.cover,
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
                        verticalArrangement = Arrangement.spacedBy(8.dp) // 给子项之间增加间距
                    ) {
                        Text(
                            detail!!.data.artist.name,
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        if (!detail!!.data.artist.transNames.isNullOrEmpty()) {
                            Text(
                                (detail!!.data.artist.transNames as Iterable<Any?>).joinToString("/"),
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
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


            itemsIndexed(hotSongs!!.songs) { index, item ->
                SongRowItem(
                    song = item,
                    onPlayClick = { /*TODO:播放歌曲*/ },
                    currentlyPlayingSongId = currentlyPlayingSongId,
                    index = index
                )
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
}

@Composable
fun SongRowItem(
    song: HotSong,
    onPlayClick: (id: Long) -> Unit,
    currentlyPlayingSongId: Long?,
    index: Int
) {
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