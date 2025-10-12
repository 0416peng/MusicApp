package com.example.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.model.home.Album
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen(viewModel: HomeViewModel= hiltViewModel()){
    LaunchedEffect(Unit) {
        viewModel.getRecommendAlbum(5)
    }
    val data by viewModel.recommendAlbum.collectAsState()
    Log.d("data",data.toString())
    Column {
        Text("推荐歌单")
        if (data!=null) {
            AlbumList(data!!.result)
        }
    }
}



@Composable
fun AlbumList(items:List<Album>){
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
        items(items){
            item->
            Card(modifier= Modifier
                .width(120.dp)
                .aspectRatio(1f)
            ) {
                AsyncImage(model = item.picUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "album image")
            }
        }
    }
}