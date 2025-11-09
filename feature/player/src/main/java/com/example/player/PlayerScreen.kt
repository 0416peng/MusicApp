package com.example.player

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.common.formatTime
import com.example.data.model.song.LyricLine
import com.example.data.model.song.parseLrc
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



const val SHOW_PIC="showPic"
const val SHOW_LYR="showLyr"
@Composable
fun PlayerScreen(
     viewModel: PlayViewModel= hiltViewModel(),
     id:Long,
){
    val currentLyricIndex by viewModel.currentLyricIndex.collectAsState()
   val currentlyPlayingSongId by viewModel.currentlyPlayingSongId.collectAsState()
    val progress by viewModel.playbackProgress.collectAsState()
    val duration by viewModel.currentSongDuration.collectAsState()
    val isPlaying: Boolean by viewModel.isPlaying.collectAsState()
    val songDetail by viewModel.songDetail.collectAsState()
    val lyricData by viewModel.lyricData.collectAsState()
    val parsedLyrics by viewModel.parsedLyrics.collectAsState()
    val infiniteTransition= rememberInfiniteTransition(label = "imageRotation")
    var sliderPosition by remember { mutableStateOf<Float?>(null) }
    var showPicOrLyr by remember { mutableStateOf<String>(SHOW_PIC) }
    LaunchedEffect(Unit,id,currentlyPlayingSongId) {
        viewModel.getSongLyric(currentlyPlayingSongId!!)
        viewModel.getSongDetail(currentlyPlayingSongId!!)
        Log.d("PlayerScreen","PlayerScreen")
    }

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 16000,
                easing = LinearEasing
            )
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        songDetail?.let {songDetail->
            Box(modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .clickable{showPicOrLyr=if (showPicOrLyr==SHOW_PIC) SHOW_LYR else SHOW_PIC},
            contentAlignment = Alignment.Center){
             if (showPicOrLyr==SHOW_PIC) {
              PicScreen(
                  modifier=Modifier.align(Alignment.Center).padding(bottom = 30.dp),
                  url = songDetail.songs[0].al.picUrl,
                  name = songDetail.songs[0].name,
                  rotationAngle=rotationAngle

              )
        }else{
            if(lyricData!=null) {
                LyricScreen(
                    modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp),
                    lyrics = parsedLyrics,
                    currentLyricIndex = currentLyricIndex,
                    onSeekTo = {time-> viewModel.seekTo(time) }
                )
            }else{
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                Text(text = "暂无歌词")}
            }
        }}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp, start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = songDetail.songs[0].name,
                        fontSize = 18.sp,

                    )
                    Text(text = songDetail.songs[0].ar.joinToString("/") { it.name },
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)){
                    Slider(value = progress.toFloat(),
                        onValueChange = {newValue->
                            sliderPosition=newValue
                        },
                        onValueChangeFinished = {
                            sliderPosition?.let {
                                viewModel.seekTo(it.toLong())
                            }
                            sliderPosition=null
                        },
                        valueRange = 0f..if(duration>0) duration.toFloat() else 1f,
                        modifier = Modifier.fillMaxWidth()
                        )}
                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = formatTime(sliderPosition?.toLong() ?:progress), fontSize = 12.sp, color = Color.Gray)
                        Text(text = formatTime(duration), fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(top=12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    IconButton(onClick = { viewModel.playOrPauseSong(id) }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "暂停/播放",
                            modifier = Modifier.size(48.dp)
                        )
                    }
            }
        }

    }
}}


@Composable
fun LyricScreen(lyrics:List<LyricLine>,
                currentLyricIndex:Int,
                modifier: Modifier,
                onSeekTo:(Long)->Unit,
                ){
    if(lyrics.isEmpty()){
        Box(modifier=modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
            ){
            Text(text = "暂无歌词",color=Color.Gray, fontSize = 16.sp)
        }
    }
    val listState= rememberLazyListState()
    val coroutineScope= rememberCoroutineScope()
    val isUserScrolling by remember { derivedStateOf { listState.isScrollInProgress } }
    var centerIndexWhileScrolling by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(currentLyricIndex) {
        if(!isUserScrolling){
        delay(100)
        coroutineScope.launch {
            listState.animateScrollToItem(currentLyricIndex)
        }}
    }
    Box(modifier= Modifier.fillMaxSize()){
        if (!isUserScrolling&&centerIndexWhileScrolling!=null){
            Box(modifier= Modifier
                .fillMaxSize()
                .clickable{
                    centerIndexWhileScrolling=null
                }
            )
        }
    }
    LazyColumn(state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 250.dp)
        ) {
        itemsIndexed(lyrics){
            index,item->

                Text(text = item.lyric,
                    fontSize = 18.sp,
                    color =if ( (index == currentLyricIndex && !isUserScrolling)) Color.Black else Color.Gray,
                  fontWeight = if ( (index == currentLyricIndex && !isUserScrolling)) Bold else Normal,
                    modifier= Modifier.padding(vertical = 12.dp)
                        .clickable{
                            onSeekTo(item.time)
                            centerIndexWhileScrolling=null
                        }
                )
        }
    }
    if(isUserScrolling){
        val centerItem=listState.layoutInfo.visibleItemsInfo.getOrNull(
            listState.layoutInfo.visibleItemsInfo.size/2
        )
        centerIndexWhileScrolling=centerItem?.index
        val centerTime=centerIndexWhileScrolling?.let { lyrics.getOrNull(it)?.time }?:0L
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 时间文本
            Text(
                text = formatTime(centerTime),
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            // 分割线
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.5f))
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun PicScreen(modifier: Modifier,
              url: String,
              name:String,
              rotationAngle: Float,
              ){
    Column(
        modifier =modifier ,
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = url,
            contentDescription =name,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .rotate(rotationAngle)
        )
    }
}