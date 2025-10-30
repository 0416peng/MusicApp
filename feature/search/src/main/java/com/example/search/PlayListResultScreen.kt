package com.example.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.model.search.data.PlayListData
import com.example.ui.LoadingPlaceholder

@Composable
fun PlayListResultScreen(
    viewModel: SearchDetailViewModel,
    onPlayListClick: (id: Long) -> Unit
) {
    val playListData by viewModel.playListsResult.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val listState = rememberLazyListState()
    if (playListData != null) {
        val playListItems = playListData!!.result.playlists.map { item ->
            PlayListData(item.name, item.id, item.coverImgUrl, item.trackCount)
        }
        LazyColumn(state = listState) {
            itemsIndexed(playListItems) { index, item ->
                PlayListItem(item, onPlayListClick)
                if (index >= playListItems.size - 3 && !isRefreshing) {
                    viewModel.loadMore()
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
    } else {
        LoadingPlaceholder()
    }
}