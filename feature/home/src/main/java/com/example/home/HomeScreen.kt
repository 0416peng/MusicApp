package com.example.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.data.model.home.DisplayableAlbumItemData

@Composable
fun HomeScreen(viewModel: HomeViewModel= hiltViewModel()){
    LaunchedEffect(Unit) {
        viewModel.getRecommendAlbum(5)
        viewModel.getNewAlbum()
    }
    val recommendAlbumData by viewModel.recommendAlbum.collectAsState()
    val newAlbumData by viewModel.newAlbum.collectAsState()
    Log.d("data",recommendAlbumData.toString())
    Column {
        Text("推荐歌单  >",
            modifier=Modifier.clickable{/*TODO*/}
            )
        if (recommendAlbumData!=null) {
            val recommendItems=recommendAlbumData!!.result.map {
                DisplayableAlbumItemData(it.name,it.picUrl)
            }
            AlbumList(recommendItems)
        }else{
            LoadingPlaceholder()
        }
        Text("最新专辑  >",
            modifier=Modifier.clickable{/*TODO*/}
            )
        if(newAlbumData!=null){
            val newAlbumItems = newAlbumData!!.albums.map {
                DisplayableAlbumItemData(name = it.name, picUrl = it.picUrl)
            }
           AlbumList(items = newAlbumItems)
        }else{
            LoadingPlaceholder()
        }
    }
}



@Composable
fun AlbumList(items:List<DisplayableAlbumItemData>){
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
        items(items){
            item->
            Column(modifier = Modifier.width(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Card(modifier= Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
                    .clickable {/*TODO*/ }
                ) {
                    AsyncImage(model = item.picUrl,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = item.name)
                }
                Text(item.name,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                    )
            }

        }
    }
}
@Composable
private fun LoadingPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
