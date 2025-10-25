package com.example.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.FakeSearchTextField
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchDetailScreen(
    viewModel: SearchDetailViewModel = hiltViewModel(),
    keyword: String,
    onBack: () -> Unit,
    onPlayListClick: (Long) -> Unit,
    onAlbumClick: (Long) -> Unit,
    onSingerClick: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onSearchTriggered(keyword)
    }

    val pagerState = rememberPagerState(initialPage = 0) { 5 }
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        Column {
            FakeSearchTextField(hint = keyword, onClick = onBack)
            TabRow(selectedTabIndex = pagerState.currentPage) {
                viewModel.categories.forEachIndexed { index, category ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                                viewModel.onTabSelected(index)
                            }

                        },
                        text = { Text(category.title) }
                    )
                }
            }
        }
    }) { paddingValues ->
        HorizontalPager(
            state = pagerState, modifier = Modifier.padding(paddingValues),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (viewModel.categories[page].type) {
                1018 -> {
                    ComprehensiveResultPage(viewModel, onPlayListClick, onAlbumClick,onSingerClick)
                    viewModel.onTabSelected(page)
                }

                1 -> {
                    SongResultScreen(viewModel)
                    viewModel.onTabSelected(page)
                }

                1000 -> {
                    PlayListResultScreen(viewModel, onPlayListClick)
                    viewModel.onTabSelected(page)
                }

                10 -> {
                    AlbumListResultScreen(viewModel, onAlbumClick)
                    viewModel.onTabSelected(page)
                }

                100 -> {
                    SingerResultScreen(viewModel, onSingerClick)
                    viewModel.onTabSelected(page)
                }
            }
        }
    }
}
