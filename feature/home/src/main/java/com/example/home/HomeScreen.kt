package com.example.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.data.model.AlbumList.Al
import com.example.data.model.home.Banner
import com.example.data.model.home.DisplayableAlbumItemData
import com.example.data.model.home.HotArtistData
import com.example.data.model.home.HotSingerData
import com.example.ui.LoadingPlaceholder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.compose


object ListType {
    const val PLAYLIST = 0
    const val ALBUM = 1
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), onAlbumClick: (Long) -> Unit,
    onPlayListClick: (Long) -> Unit
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
        SearchTextField(
            value = searchText,
            onValueChange = { newText -> viewModel.onSearchTextChanged(newText) },
            onSearch = { text ->/*TODO:搜索逻辑*/ }
        )

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





