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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.model.search.data.SongData
import com.example.ui.LoadingPlaceholder

@Composable
fun SongResultScreen(viewModel: SearchDetailViewModel){
    val songsData by viewModel.songsResult.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val listState= rememberLazyListState()
    if(songsData!=null){
       val songsItem= songsData!!.result.songs.map {
           SongData(it.id, it.name,it.artists.joinToString("/"){ar->ar.name})
       }
        LazyColumn(state = listState) {
            itemsIndexed(songsItem){index,item->
                val isPlaying = currentlyPlayingSongId == item.id
                val color = if (isPlaying) Color.Red else Color.Black
                SongItem(item,color,onPlayClick = {id->viewModel.onPlayPauseClicked(id)})
                if(index>=songsItem.size-3&&!isRefreshing){
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

    }
    else{
        LoadingPlaceholder()
    }
}