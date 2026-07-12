package com.example.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.model.home.DisplayableAlbumItemData
import com.example.home.HomeViewModel
import com.example.ui.LoadingPlaceholder
import com.example.ui.TopBackBar

@Composable
fun RecommendPlaylistScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onPlayListClick: (Long) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.getRecommendAlbum(50) }

    val recommendAlbumData by viewModel.recommendAlbum.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBackBar(title = "推荐歌单", onBack = onBack)

        if (recommendAlbumData == null) {
            LoadingPlaceholder()
        } else {
            val items = recommendAlbumData!!.result.map {
                DisplayableAlbumItemData(it.name, it.picUrl, it.id)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items, key = { it.albumId }) { item ->
                    Column {
                        Card(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clickable { onPlayListClick(item.albumId) },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            AsyncImage(
                                model = item.picUrl,
                                contentDescription = item.name,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Text(
                            item.name,
                            fontSize = 12.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
