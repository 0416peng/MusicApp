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
import com.example.common.formatTimestamp
import com.example.data.model.albumList.AlbumListData
import com.example.ui.LoadingPlaceholder

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
            if (albumListData != null) {
                AsyncImage(
                    model = albumListData.album.picUrl,
                    modifier = Modifier
                        .size(130.dp)
                        .padding(16.dp)
                        .clickable {/*TODO：页面跳转*/ }
                        .clip(RoundedCornerShape(8.dp)),
                    contentDescription = "album Picture"
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(
                        albumListData.album.name,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "歌手:" + albumListData.album.artist.name + " >",
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {/*TODO：页面跳转*/ }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "发行时间:" + formatTimestamp(albumListData.album.publishTime),
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadingPlaceholder()
                }
            }
        }
    }//顶部的专辑信息
}