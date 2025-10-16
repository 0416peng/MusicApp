package com.example.ui.AlbumList


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.formatTimestamp
import com.example.ui.LoadingPlaceholder

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel = hiltViewModel(), id: Int) {

    LaunchedEffect(Unit) {
        viewModel.getAlbumList(id)
    }
    val albumListData by viewModel.albumListData.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(color = Color(0xFF0D10EC))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(
                        colors=listOf(Color(0xFF0D10EC),
                           Color( 0xFF866171)
                    ))
            )){if (albumListData!=null){
                AsyncImage(
                    model = albumListData!!.album.picUrl,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .clickable{/*TODO*/}
                    ,
                    contentDescription = "album Picture"
                )
                Column {
                    Text(albumListData!!.album.name,
                        fontSize = 15.sp
                        )
                    Text("歌手:"+albumListData!!.album.artist.name+" >",
                        fontSize = 10.sp,
                        modifier = Modifier
                            .clickable{/*TODO*/}
                        )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text("发行时间:"+ formatTimestamp(albumListData!!.album.publishTime),
                        fontSize = 10.sp
                    )
                }
            }else{
                Box(modifier = Modifier.fillMaxSize()){
                    LoadingPlaceholder()
            }
                }
            }
        }//顶部的专辑信息
    }
    LazyColumn {
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable{/*TODO:实现播放全部的逻辑*/}
                .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.PlayArrow,
                    contentDescription = "播放全部",
                    modifier = Modifier.padding(12.dp)
                    )
                Text("播放全部")
            }
        }
        items(albumListData!!.songs){
            item->
        }
    }
}
