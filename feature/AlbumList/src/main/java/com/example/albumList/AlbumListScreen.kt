package com.example.albumList


import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.LoadingPlaceholder

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel = hiltViewModel(), id: Long) {
    LaunchedEffect(Unit) {
        viewModel.getAlbumList(id)
    }
    val albumListData by viewModel.albumListData.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val context = LocalContext.current
    val errorState by viewModel.errorState.collectAsState()
    LaunchedEffect(errorState) {
        if (errorState != null) {
            Toast.makeText(context, errorState, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
    }
    if (albumListData?.code == 200) {
        AlbumList(
            songs = albumListData!!.songs,
            currentlyPlayingSongId = currentlyPlayingSongId,
            header = { Album(albumListData) },
            onClick = { index -> viewModel.onAddListClicked(index) }
        )
    } else {
        LoadingPlaceholder()
    }
}




