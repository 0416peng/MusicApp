
package com.example.musicapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicapp.PlayerViewModel


@Composable
fun MiniPlayer(
    viewModel: PlayerViewModel = hiltViewModel()
){
    val songsData by viewModel.songsList.collectAsState()
   if(songsData!=null){
       Row(modifier = Modifier
           .fillMaxWidth()
           .height(64.dp)
           .padding(horizontal = 8.dp),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.spacedBy(8.dp)
           ) {
           Text(text =)
       }
   }
}