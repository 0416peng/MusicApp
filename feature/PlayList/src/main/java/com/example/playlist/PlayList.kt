package com.example.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.playList.PlayListDetailData

@Composable
fun PlayList(playListDetailData: PlayListDetailData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0D10EC),
                            Color(0xFF866171)
                        )
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = playListDetailData.playlist.coverImgUrl,
                modifier = Modifier
                    .size(130.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = "playList Picture"
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    playListDetailData.playlist.name,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}