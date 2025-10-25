package com.example.artist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SongsScreen(id: Long,
                viewModel: ArtistViewModel = hiltViewModel()
                ){
    LaunchedEffect(Unit) {

    }
}