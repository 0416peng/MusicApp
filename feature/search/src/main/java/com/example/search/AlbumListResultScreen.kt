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
import com.example.data.model.search.data.AlbumData
import com.example.ui.LoadingPlaceholder


@Composable
fun AlbumListResultScreen(viewModel: SearchDetailViewModel,
                          onAlbumClick:(id:Long)->Unit
                          ){
    val albumData by viewModel.albumsResult.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val listState= rememberLazyListState()
    if (albumData!=null){
        val albumItems=albumData!!.result.albums.map {
            item->
            AlbumData(item.name, item.id, item.picUrl, item.artist.name)
        }
        LazyColumn(state = listState) {
            itemsIndexed(albumItems){
                index,item->
                AlbumListItem(item,onAlbumClick)
                if(index>=albumItems.size-3&&!isRefreshing){
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

    }else{
        LoadingPlaceholder()
    }
}