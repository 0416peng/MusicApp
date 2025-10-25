package com.example.albumList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.albumList.Song

@Composable
fun AlbumList(songs: List<Song>, currentlyPlayingSongId: Long?, onClick:(id: Long)-> Unit) {

    LazyColumn {
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
        items(songs) { item ->
            val isPlaying = currentlyPlayingSongId==item.id
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {/*TODO:播放音乐*/onClick(item.id) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if(isPlaying){
                    Icon(imageVector = Icons.Filled.Pause,
                        contentDescription = "正在播放",
                        tint = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }else{
                    Text("${songs.indexOf(item)+1}",
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    val color = if (isPlaying) Color.Red else Color.Black
                    Text(item.name,
                        fontSize = 14.sp,
                        color=color
                    )
                    Text(item.ar.joinToString("/"){it.name},
                        fontSize = 10.sp,

                        )
                }
                Icon(imageVector = Icons.Default.MoreVert,
                    contentDescription = "更多选项",
                    modifier = Modifier.clickable{/*TODO:歌曲菜单*/}
                )
            }
        }
    }
}