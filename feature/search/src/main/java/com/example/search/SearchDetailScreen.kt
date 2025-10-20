package com.example.search

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchDetailScreen(
    viewModel: SearchDetailViewModel= hiltViewModel(),
    keyword: String
){
    LaunchedEffect(Unit) {
        viewModel.onSearchTriggered(keyword,1018)
    }
    val data by viewModel.detailResult.collectAsState()
    Log.d("data",data.toString())
}