package com.example.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.home.HotArtistData

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