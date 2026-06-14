package com.example.artist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.artist.Song
import com.example.ui.LoadingPlaceholder
import com.example.ui.TopBackBar

@Composable
fun ArtistAllSongsScreen(
    name: String,
    id: Long,
    viewModel: ArtistViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.getArtistSongs(id) }

    val songs by viewModel.songs.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopBackBar(title = name, onBack = onBack) }
    ) { innerPadding ->
        if (songs == null) {
            LoadingPlaceholder()
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                itemsIndexed(songs!!.songs) { index, item ->
                    AllSongRowItem(
                        song = item,
                        onPlayClick = { viewModel.onAddListClicked(index) },
                        currentlyPlayingSongId = viewModel.currentlyPlayingSongId.collectAsState().value,
                        index = index
                    )

                    // 滑到倒数第 3 项且还有更多数据时触发加载
                    if (index >= songs!!.songs.size - 3 && songs!!.more && !isRefreshing) {
                        LaunchedEffect(Unit) {
                            viewModel.getArtistSongs(id)
                        }
                    }
                }

                // 加载更多时的底部 loading
                if (isRefreshing) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AllSongRowItem(
    song: Song,
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
