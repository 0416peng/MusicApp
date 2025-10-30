package com.example.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.home.DisplayableAlbumItemData

@Composable
fun AlbumList(
    items: List<DisplayableAlbumItemData>,
    onAlbumClick: (Long) -> Unit,
    onPlayListClick: (Long) -> Unit,
    type: Int
) {
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
                            if (type == 0) onPlayListClick(item.albumId)
                            else onAlbumClick(item.albumId)
                        }


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
                )
            }

        }
    }
}
