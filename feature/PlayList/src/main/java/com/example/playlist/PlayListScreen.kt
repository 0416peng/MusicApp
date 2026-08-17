package com.example.playlist

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.LoadingPlaceholder
import com.example.ui.TopBackBar


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayListScreen(
    id: Long,
    onBack: () -> Unit,
    viewModel: PlayListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getPlayListData(id)
        viewModel.getPlayListDetail(id)
    }
    val playListData by viewModel.playListData.collectAsState()
    val playListDetailData by viewModel.playListDetailData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val listState = rememberLazyListState()
    val showTopBar by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val context = LocalContext.current
    val errorState by viewModel.errorState.collectAsState()
    LaunchedEffect(errorState) {
        if (errorState != null) {
            Toast.makeText(context, errorState, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
    }
    if (playListData?.code == 200) {
        Box(modifier = Modifier.fillMaxSize()) {
            SongList(
                playListData = playListData,
                currentlyPlayingSongId = currentlyPlayingSongId,
                listState = listState,
                header = {
                    if (playListDetailData?.code == 200) {
                        PlayList(playListDetailData!!)
                    } else {
                        LoadingPlaceholder()
                    }
                },
                onAddListClick = { index -> viewModel.onAddListClicked(index) },
                loadMore = { viewModel.loadMorePlayListData(id) },
                isRefreshing = isRefreshing
            )
            AnimatedVisibility(
                visible = showTopBar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopBackBar(
                    title = playListDetailData?.playlist?.name ?: "歌单",
                    onBack = onBack
                )
            }
        }
    } else {
        LoadingPlaceholder()
    }
}





