package com.example.artist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
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
    // 收集一次，不要在每个 item 里重复 collectAsState
    val currentSongId by viewModel.currentlyPlayingSongId.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopBackBar(title = name, onBack = onBack) }
    ) { innerPadding ->
        if (songs == null) {
            LoadingPlaceholder()
        } else {
            val songList = songs!!.songs
            val more = songs!!.more
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                itemsIndexed(
                    items = songList,
                    key = { index, item -> item.id }
                ) { index, item ->
                    AllSongRowItem(
                        song = item,
                        onPlayClick = { viewModel.onAddListClicked(index) },
                        currentlyPlayingSongId = currentSongId,
                        index = index
                    )

                    if (index >= songList.size - 3 && more && !isRefreshing) {
                        LaunchedEffect(Unit) { viewModel.getArtistSongs(id) }
                    }
                }

                if (isRefreshing) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator() }
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
            modifier = Modifier.padding(end = 12.dp).size(28.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isPlaying) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "正在播放",
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(text = "${index + 1}", color = Color.Gray, fontSize = 14.sp)
            }
        }



        Column(
            modifier = Modifier.weight(1f).padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = song.name, fontSize = 15.sp,
                color = if (isPlaying) Color.Red else Color.Black,
                maxLines = 1
            )
            Text(
                text = song.ar.joinToString("/") { it.name },
                fontSize = 11.sp, color = Color.Gray, maxLines = 1
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
