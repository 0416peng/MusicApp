
package com.example.musicapp.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.data.model.song.SongsListData
import com.example.musicapp.PlayerViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniPlayer(
    viewModel: PlayerViewModel = hiltViewModel()
){
    val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val songsData by viewModel.songsList.collectAsState()
    val songDetail by viewModel.songDetailData.collectAsState()
    var showList by remember{mutableStateOf(false)}
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val errorState by viewModel.errorState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    LaunchedEffect(currentlyPlayingSongId) {
        if(currentlyPlayingSongId!=null){
            Log.d("currentlyPlayingSongId",currentlyPlayingSongId.toString())
        viewModel.getSongDetail(currentlyPlayingSongId!!)
    }}
    LaunchedEffect(songDetail) {
        Log.d("songDetail",songDetail.toString())
    }
    LaunchedEffect(errorState) {
        Log.d("error",errorState.toString())
    }
    val isPlaying=true
        if (songDetail!=null) {
            Log.d("songDetail",songDetail.toString())
            if (showList) {
                ModalBottomSheet(
                    onDismissRequest = { showList = false },
                    sheetState = sheetState
                ) {
                    songsData?.let { list ->
                        PlayListSheet(
                            items = list,
                            onClick = { index -> viewModel.addMultipleToQueue(index) })
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showList = false
                            }
                        }
                    }
                }}
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = songDetail!!.songs[0].al.picUrl,
                        contentDescription = songDetail!!.songs[0].name,
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .size(48.dp)
                            .clickable {/*TODO:跳转到歌曲详情页*/ },
                        contentScale = ContentScale.Crop
                    )
                    Text(text = songDetail!!.songs[0].name)
                    IconButton(onClick = { viewModel.playOrPauseSong(songDetail!!.songs[0].id) }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "暂停" else "播放",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = { showList = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "列表",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }



@Composable
fun PlayListSheet(items:List<SongsListData>,onClick:(Int)->Unit){
    LazyColumn(modifier = Modifier.padding(bottom = 16.dp)) {
        itemsIndexed(items){
            index,item->
            Text(text = "${index+1}.${item.name}",
                modifier=Modifier
                    .clickable {
                        onClick(index)
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}