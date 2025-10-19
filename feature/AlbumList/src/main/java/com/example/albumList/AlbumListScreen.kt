package com.example.albumList


import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.formatTimestamp
import com.example.data.model.albumList.AlbumListData
import com.example.data.model.albumList.Song
import com.example.ui.LoadingPlaceholder

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel = hiltViewModel(), id: Long) {
    LaunchedEffect(Unit) {
        viewModel.getAlbumList(id)
    }
    val albumListData by viewModel.albumListData.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val context= LocalContext.current
    val errorState by viewModel.errorState.collectAsState()
    LaunchedEffect(errorState) {
        if (errorState != null){
            Toast.makeText(context,errorState, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if(albumListData!=null){
            if(albumListData!!.code ==200){
                Album(albumListData!!)
            }
        }
        else{
            Box(modifier = Modifier.fillMaxSize()) {
                LoadingPlaceholder()
            }
        }
        if (albumListData!=null){
            if(albumListData!!.code==200){
                AlbumList(albumListData!!.songs,currentlyPlayingSongId, onClick = {id-> viewModel.onPlayPauseClicked( id) })
            }
        }else{
            Box(modifier = Modifier.fillMaxSize()) {
                LoadingPlaceholder()
            }
        }

    }

}


@Composable
fun Album(albumListData: AlbumListData?){
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
                Column(modifier = Modifier.padding(16.dp)
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