package com.example.home.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.model.home.Banner
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(items: List<Banner>) {
    val pagerState = rememberPagerState(
        initialPage = if (items.isNotEmpty()) items.size * 10 else 0,
        pageCount = { items.size * 20 }
    )
    if (items.isNotEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
                try {
                    val pageCount = pagerState.pageCount
                    if (pageCount == 0) continue
                    val nextPage = (pagerState.currentPage + 1) % pageCount
                    pagerState.animateScrollToPage(nextPage)
                } catch (e: Exception) {
                    Log.d("error", e.message.toString())
                }//通过try-catch防止用户滑动打断while循环
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val item = items[page % items.size]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.5f)
                    .clickable {/*TODO*/ }
            ) {
                AsyncImage(
                    model = item.pic,
                    contentDescription = "banner",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items.indices.forEach { index ->
                val color = if ((pagerState.currentPage % items.size) == index) {
                    Color.Red
                } else {
                    Color.Gray
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}