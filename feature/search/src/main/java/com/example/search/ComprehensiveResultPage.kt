package com.example.search

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.search.data.AlbumData
import com.example.data.model.search.data.PlayListData
import com.example.data.model.search.data.SongData
import com.example.ui.LoadingPlaceholder

@Composable
fun ComprehensiveResultPage(
    viewModel: SearchDetailViewModel,
    onPlayListClick: (Long) -> Unit,
    onAlbumClick: (Long) -> Unit
) {

    val detailData by viewModel.detailResult.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (detailData != null) {
            item {
                Row(
                    Modifier.fillMaxWidth().clickable{/*TODO:跳转到歌手详情页*/},
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
                    Modifier.fillMaxWidth().clickable{onAlbumClick(detailData!!.result.album.albums[0].id)},
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
                    Modifier.fillMaxWidth().clickable{onPlayListClick(detailData!!.result.playList.playLists[0].id)},
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
                PlayListData(it.name,it.id,it.coverImgUrl, it.trackCount)
            }
            items(playListItems) { item ->
                PlayListItem(item, onPlayListClick = {onPlayListClick(item.id)})
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
                    "专辑精选",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    fontSize = 25.sp
                )
            }
            val albumItems = detailData!!.result.album.albums.take(5).map {
                AlbumData(it.name,it.id,it.picUrl,it.artist.name)
            }
            items(albumItems){item->
                AlbumListItem(item, onAlbumClick = {onAlbumClick(item.id)})
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
