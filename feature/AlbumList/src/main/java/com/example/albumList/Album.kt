package com.example.albumList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.formatTimestamp
import com.example.data.model.albumList.AlbumListData
import com.example.ui.LoadingPlaceholder
import com.example.ui.thumbnailUrl

private val HEADER_GRADIENT = listOf(
    Color(0xFF141528),
    Color(0xFF2D1B69),
)

@Composable
fun Album(albumListData: AlbumListData?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(colors = HEADER_GRADIENT)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (albumListData != null) {
                Card(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(140.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    AsyncImage(
                        model = thumbnailUrl(albumListData.album.picUrl, 600),
                        contentDescription = "专辑封面",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { /*TODO：页面跳转*/ },
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        albumListData.album.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "歌手: ${albumListData.album.artist.name} >",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { /*TODO：页面跳转*/ }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "发行时间: ${formatTimestamp(albumListData.album.publishTime)}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadingPlaceholder()
                }
            }
        }
    }
}
