package com.example.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.model.search.data.PlayListData
import com.example.data.model.search.data.SongData
import com.example.ui.FakeSearchTextField
import com.example.ui.LoadingPlaceholder

@Composable
fun SearchDetailScreen(
    viewModel: SearchDetailViewModel = hiltViewModel(),
    keyword: String,
    onBack: () -> Unit,
    onPlayListClick: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onSearchTriggered(keyword, 1018)
    }
    val detailData by viewModel.detailResult.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            FakeSearchTextField(hint = keyword, onClick =onBack)
        }

        if (detailData != null) {
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = detailData!!.result.artist.artists[0].picUrl,
                        contentDescription = detailData!!.result.artist.artists[0].name,
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "歌手: ${detailData!!.result.artist.artists[0].name}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = detailData!!.result.album.albums[0].picUrl,
                        contentDescription = detailData!!.result.album.albums[0].name,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(50.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "专辑: ${detailData!!.result.album.albums[0].name}",
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = detailData!!.result.playList.playLists[0].coverImgUrl,
                        contentDescription = detailData!!.result.playList.playLists[0].name,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(50.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "歌单: ${detailData!!.result.playList.playLists[0].name}",
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.3f)
                )
            }
            item {
                Text(
                    "单曲精选",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    fontSize = 25.sp
                )
            }

            val songItems = detailData!!.result.song.songs.take(10).map {
                SongData(it.id, it.name, it.ar.joinToString("/") { ar -> ar.name })
            }
            items(songItems) { item ->
                val isPlaying = currentlyPlayingSongId == item.id
                val color = if (isPlaying) Color.Red else Color.Black
                SongItem(item,color,onPlayClick = {id->viewModel.onPlayPauseClicked(id)})
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.3f)
                )
            }
            item {
                Text(
                    "歌单精选",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    fontSize = 25.sp
                )
            }
            val playListItems = detailData!!.result.playList.playLists.take(5).map {
                PlayListData(it.name,it.id,it.coverImgUrl)
            }
            items(playListItems) { item ->
               PlayListItem(item, onPlayListClick = {onPlayListClick(item.id)})
            }

        } else {
            item {
                Row(modifier = Modifier.fillParentMaxSize()) {
                    LoadingPlaceholder()
                }
            }
        }
    }
}


@Composable
fun SongItem(item: SongData,color: Color,onPlayClick:(id: Long)->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlayClick(item.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(item.name, fontSize = 16.sp, color = color)
            Text(item.artist, fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "更多选项",
            modifier = Modifier.clickable {/*TODO:歌曲菜单*/ }
        )
    }
}
@Composable
fun PlayListItem(item: PlayListData,onPlayListClick:(id: Long)-> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlayListClick(item.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(model = item.picUrl, contentDescription = item.name, modifier = Modifier.padding(12.dp).size(45.dp))
        Column(modifier = Modifier.padding(12.dp).weight(1f)) {
            Text(item.name, fontSize = 16.sp)
        }
    }
}