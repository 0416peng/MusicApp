package com.example.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.playList.Song

@Composable
fun SongItem(song: Song, currentlyPlayingSongId: Long?, onClick: (id: Long) -> Unit) {
    val isPlaying = currentlyPlayingSongId == song.id
    val color = if (isPlaying) Color.Red else Color.Black
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(song.id) }) {
        AsyncImage(
            model = song.al.picUrl,
            modifier = Modifier
                .size(60.dp)
                .padding(12.dp),
            contentDescription = "song picture"
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                song.name,
                fontSize = 14.sp,
                color = color
            )
            Text(
                song.ar.joinToString("/") { it.name },
                fontSize = 10.sp
            )

        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "更多选项",
            modifier = Modifier.clickable {/*TODO:歌曲菜单*/ }
        )
    }
}