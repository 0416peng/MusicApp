package com.example.albumList


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
                AlbumList(albumListData!!.songs,currentlyPlayingSongId, onClick = {index-> viewModel.onAddListClicked(index) })
            }
        }else{
            Box(modifier = Modifier.fillMaxSize()) {
                LoadingPlaceholder()
            }
        }

    }

}




