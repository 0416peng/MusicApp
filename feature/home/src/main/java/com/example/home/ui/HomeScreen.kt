package com.example.home.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.data.model.home.DisplayableAlbumItemData
import com.example.home.HomeViewModel
import com.example.ui.FakeSearchTextField
import com.example.ui.LoadingPlaceholder
import com.example.ui.SearchTextField


object ListType {
    const val PLAYLIST = 0
    const val ALBUM = 1
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), onAlbumClick: (Long) -> Unit,
    onPlayListClick: (Long) -> Unit,
    onSearchClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getRecommendAlbum(5)
        viewModel.getNewAlbum()
        viewModel.getBanner()
        viewModel.getHotSinger()
        viewModel.getTopList()
    }
    val recommendAlbumData by viewModel.recommendAlbum.collectAsState()
    val newAlbumData by viewModel.newAlbum.collectAsState()
    val bannerData by viewModel.banner.collectAsState()
    val hotSingerData by viewModel.hotSinger.collectAsState()
    val topListData by viewModel.topList.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val context = LocalContext.current
    val searchText by viewModel.searchText.collectAsState()
    LaunchedEffect(errorState) {
        if (errorState != null) {
            Toast.makeText(context, errorState, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
    }
    Column {
        FakeSearchTextField(hint = "搜索", onClick = onSearchClick)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            if (bannerData != null) {
                Banner(bannerData!!.banners)
            } else {
                LoadingPlaceholder()
            }

            Text(
                "推荐歌单  >",
                modifier = Modifier
                    .clickable {/*TODO*/ }
                    .padding(12.dp),
            )
            if (recommendAlbumData != null) {
                val recommendItems = recommendAlbumData!!.result.map {
                    DisplayableAlbumItemData(it.name, it.picUrl, it.id)
                }
                AlbumList(recommendItems, onAlbumClick, onPlayListClick, ListType.PLAYLIST)
            } else {
                LoadingPlaceholder()
            }
            Text(
                "最新专辑  >",
                modifier = Modifier
                    .clickable {/*TODO*/ }
                    .padding(12.dp)
            )
            if (newAlbumData != null) {
                val newAlbumItems = newAlbumData!!.albums.map {
                    DisplayableAlbumItemData(name = it.name, picUrl = it.picUrl, it.id)
                }
                AlbumList(items = newAlbumItems, onAlbumClick, onPlayListClick, ListType.ALBUM)
            } else {
                LoadingPlaceholder()
            }
            Text(
                "排行榜 >",
                modifier = Modifier
                    .clickable {/*TODO*/ }
                    .padding(12.dp)
            )
            if (topListData != null) {
                val newAlbumItems = topListData!!.list.take(9).map {
                    DisplayableAlbumItemData(name = it.name, picUrl = it.coverImgUrl, it.id)
                }
                AlbumList(items = newAlbumItems, onAlbumClick, onPlayListClick, ListType.PLAYLIST)
            } else {
                LoadingPlaceholder()
            }
            Text(
                "热门歌手",
                modifier = Modifier
                    .clickable {/*TODO*/ }
                    .padding(12.dp)
            )
            if (hotSingerData != null) {
                HotSingerList(hotSingerData!!.artists)
            } else {
                LoadingPlaceholder()
            }

        }
    }
}





