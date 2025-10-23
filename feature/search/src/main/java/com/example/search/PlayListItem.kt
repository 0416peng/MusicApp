package com.example.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.search.data.PlayListData

@Composable
fun PlayListItem(item: PlayListData,onPlayListClick:(id: Long)-> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlayListClick(item.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(model = item.picUrl, contentDescription = item.name, modifier = Modifier.padding(12.dp).size(45.dp))
        Column(modifier = Modifier.padding(12.dp).weight(1f)) {
            Text(item.name, fontSize = 16.sp)
            Text(item.trackCount.toString() + "首", fontSize = 12.sp)
        }
    }
}