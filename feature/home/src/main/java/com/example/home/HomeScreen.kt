package com.example.home

import android.util.Log
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.data.model.home.Banner
import com.example.data.model.home.DisplayableAlbumItemData
import com.example.data.model.home.HotArtistData
import com.example.data.model.home.HotSingerData
import com.example.ui.LoadingPlaceholder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.compose

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(),onAlbumClick:(Long)->Unit) {
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
                DisplayableAlbumItemData(it.name, it.picUrl,it.id)
            }
            AlbumList(recommendItems,onAlbumClick)
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
                DisplayableAlbumItemData(name = it.name, picUrl = it.picUrl,it.id)
            }
            AlbumList(items = newAlbumItems,onAlbumClick)
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
                DisplayableAlbumItemData(name = it.name, picUrl = it.coverImgUrl,it.id)
            }
            AlbumList(items = newAlbumItems,onAlbumClick)
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


@Composable
fun AlbumList(items: List<DisplayableAlbumItemData>,onAlbumClick:(Long)->Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Column(
                modifier = Modifier.width(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(1f)
                        .clickable {
                            Log.d("albumId",item.albumId.toString())
                            onAlbumClick(item.albumId) }
                ) {
                    AsyncImage(
                        model = item.picUrl,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = item.name
                    )
                }
                Text(
                    item.name,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {}
                )
            }

        }
    }
}


//轮播图
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(items: List<Banner>) {
    val pagerState = rememberPagerState(
        initialPage = if (items.isNotEmpty()) items.size * 10 else 0,
        pageCount = { items.size * 20 }
    )
    if (items.isNotEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
                try {
                    val pageCount = pagerState.pageCount
                    if (pageCount == 0) continue
                    val nextPage = (pagerState.currentPage + 1) % pageCount
                    pagerState.animateScrollToPage(nextPage)
                } catch (e: Exception) {
                    Log.d("error", e.message.toString())
                }//通过try-catch防止用户滑动打断while循环
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val item = items[page % items.size]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.5f)
                    .clickable {/*TODO*/ }
            ) {
                AsyncImage(
                    model = item.pic,
                    contentDescription = "banner",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items.indices.forEach { index ->
                val color = if ((pagerState.currentPage % items.size) == index) {
                    Color.Red
                } else {
                    Color.Gray
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun HotSingerList(items: List<HotArtistData>) {
    LazyHorizontalGrid(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        rows = GridCells.Fixed(3),
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .width(150.dp)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = item.picUrl,
                    modifier = Modifier
                        .size(64.dp),
                    contentDescription = item.name
                )
                Text(
                    item.name,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
            }


        }
    }
}



