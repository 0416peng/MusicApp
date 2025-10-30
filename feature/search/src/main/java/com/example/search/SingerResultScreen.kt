package com.example.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.search.Artist
import com.example.ui.LoadingPlaceholder

@Composable
fun SingerResultScreen(
    viewModel: SearchDetailViewModel,
    onSingerClick: (id: Long) -> Unit
) {
    val singerData by viewModel.singerResult.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val listState = rememberLazyListState()
    if (singerData != null) {
        LazyColumn(state = listState) {
            itemsIndexed(singerData!!.result.artists) { index, item ->
                SingerItem(item, onSingerClick)
                if (index >= singerData!!.result.artists.size - 3 && !isRefreshing) {
                    viewModel.loadMore()
                }
            }
        }
    } else {
        LoadingPlaceholder()
    }
}

@Composable
fun SingerItem(item: Artist, onSingerClick: (id: Long) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSingerClick(item.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.picUrl,
            contentDescription = item.name,
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .size(45.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            if (item.trans != null) {
                Text(
                    item.name + "(${item.transNames})",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp
                )
            } else {
                Text(
                    item.name,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp
                )
            }

        }
    }
}
