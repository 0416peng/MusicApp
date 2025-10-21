package com.example.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ui.FakeSearchTextField

@Composable
fun SearchDetailScreen(
    viewModel: SearchDetailViewModel = hiltViewModel(),
    keyword: String
) {
    LaunchedEffect(Unit) {
        viewModel.onSearchTriggered(keyword, 1018)
    }
    val detailData by viewModel.detailResult.collectAsState()

    Column {
        FakeSearchTextField(hint = "搜索", onClick = {/*TODO:跳转到搜索详情页*/ })
        if (detailData != null) {
            LazyColumn() {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = detailData!!.result.artist.artists[0].picUrl,
                            contentDescription = detailData!!.result.artist.artists[0].name,
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape)
                                .size(24.dp),
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
                    Row(Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = detailData!!.result.album.albums[0].picUrl,
                            contentDescription = detailData!!.result.album.albums[0].name,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
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
                    Row(Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = detailData!!.result.playList.playLists[0].coverImgUrl,
                            contentDescription = detailData!!.result.playList.playLists[0].name,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "歌单: ${detailData!!.result.playList.playLists[0].name}",
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

            }

        }
    }

}