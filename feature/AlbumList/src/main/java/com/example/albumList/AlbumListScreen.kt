package com.example.albumList


import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.LoadingPlaceholder
import com.example.ui.TopBackBar

@Composable
fun AlbumListScreen(
    id: Long,
    onBack: () -> Unit,
    viewModel: AlbumListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getAlbumList(id)
    }
    val albumListData by viewModel.albumListData.collectAsState()
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val listState = rememberLazyListState()
    val showTopBar by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val context = LocalContext.current
    val errorState by viewModel.errorState.collectAsState()
    LaunchedEffect(errorState) {
        if (errorState != null) {
            Toast.makeText(context, errorState, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
    }
    if (albumListData?.code == 200) {
        Box(modifier = Modifier.fillMaxSize()) {
            AlbumList(
                songs = albumListData!!.songs,
                currentlyPlayingSongId = currentlyPlayingSongId,
                listState = listState,
                header = { Album(albumListData) },
                onClick = { index -> viewModel.onAddListClicked(index) }
            )
            AnimatedVisibility(
                visible = showTopBar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopBackBar(title = albumListData!!.album.name, onBack = onBack)
            }
        }
    } else {
        LoadingPlaceholder()
    }
}




