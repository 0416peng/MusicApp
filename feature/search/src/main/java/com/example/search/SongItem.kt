package com.example.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.search.data.SongData

@Composable
fun SongItem(item: SongData,color: Color,onPlayClick:(index: Int)->Unit,index:Int){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlayClick(index) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(item.name, fontSize = 16.sp, color = color)
            Text(item.artist, fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "更多选项",
            modifier = Modifier.clickable {/*TODO:歌曲菜单*/ }
        )
    }
}