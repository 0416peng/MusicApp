package com.example.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.model.playList.PlayListData

@Composable
fun SongList(
    playListData: PlayListData?,
    currentlyPlayingSongId: Long?,
    listState: LazyListState,
    onAddListClick: (index: Int) -> Unit,
    loadMore: (id: Long) -> Unit,
    isRefreshing: Boolean
) {
    if (playListData != null) {
        if (playListData.code == 200) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {/*TODO:实现播放全部的逻辑*/ }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "播放全部",
                            modifier = Modifier.padding(12.dp)
                        )
                        Text("播放全部")
                    }
                }
                itemsIndexed(
                    items = playListData.songs,
                ) { index, item ->
                    SongItem(
                        song = item, currentlyPlayingSongId,
                        { onAddListClick(index) })
                    val totalItemsCount = playListData.songs.size
                    if (index >= totalItemsCount - 3 && !isRefreshing) {
                        LaunchedEffect(Unit) {
                            loadMore(item.id)
                        }
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
                }//加载的ui
            }
        }


    }
}