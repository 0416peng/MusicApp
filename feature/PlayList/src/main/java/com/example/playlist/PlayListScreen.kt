package com.example.playlist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.playList.PlayListDetailData
import com.example.data.model.playList.Song
import com.example.ui.LoadingPlaceholder
import kotlin.collections.joinToString


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayListScreen(viewModel: PlayListViewModel = hiltViewModel(), id: Long) {
    LaunchedEffect(Unit) {
        viewModel.getPlayListData(id)
        viewModel.getPlayListDetail(id)
    }
    val playListData by viewModel.playListData.collectAsState()
    val playListDetailData by viewModel.playListDetailData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val listState = rememberLazyListState()
    val context= LocalContext.current
    val errorState by viewModel.errorState.collectAsState()
    LaunchedEffect(errorState) {
        if (errorState != null){
            Toast.makeText(context,errorState, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (playListDetailData != null) {
            if (playListDetailData!!.code == 200) {
                PlayList(playListDetailData!!)
            }
        } else {
            LoadingPlaceholder()
        }
        if (playListData != null) {
            if (playListData!!.code == 200) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {/*TODO:实现播放全部的逻辑*/ }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "播放全部",
                                modifier = Modifier.padding(12.dp)
                            )
                            Text("播放全部")
                        }
                    }
                    itemsIndexed(
                        items = playListData!!.songs,
                    ) { index, item ->
                        SongItem(song = item, currentlyPlayingSongId,
                            {id-> viewModel.onPlayPauseClicked(id) })
                        val totalItemsCount = playListData!!.songs.size
                        if (index >= totalItemsCount - 3 && !isRefreshing) {
                            LaunchedEffect(Unit) {
                                viewModel.loadMorePlayListData(id)
                            }
                        }

                    }
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
}


@Composable
fun PlayList(playListDetailData: PlayListDetailData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0D10EC),
                            Color(0xFF866171)
                        )
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = playListDetailData.playlist.coverImgUrl,
                modifier = Modifier
                    .size(130.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = "playList Picture"
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    playListDetailData.playlist.name,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SongItem(song: Song, currentlyPlayingSongId: Long?,onClick:(id:Long)->Unit,) {
    val isPlaying = currentlyPlayingSongId == song.id
    val color = if (isPlaying) Color.Red else Color.Black
    Row(modifier = Modifier.fillMaxWidth()
        .clickable{onClick(song.id)}) {
        AsyncImage(
            model = song.al.picUrl,
            modifier = Modifier
                .size(60.dp)
                .padding(12.dp)

            ,
            contentDescription = "song picture"
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                song.name,
                fontSize = 14.sp,
                color = color
            )
            Text(
                song.ar.joinToString("/") { it.name },
                fontSize = 10.sp
            )

        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "更多选项",
            modifier = Modifier.clickable {/*TODO:歌曲菜单*/ }
        )
    }
}
