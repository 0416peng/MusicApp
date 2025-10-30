package com.example.playlist

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.LoadingPlaceholder


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
        SongList(playListData,currentlyPlayingSongId,listState,
            {index-> viewModel.onAddListClicked(index)},
            {id-> viewModel.loadMorePlayListData(id)},
            isRefreshing
            )
    }
}





