package com.example.musicapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.albumList.AlbumListScreen
import com.example.artist.ArtistScreen
import com.example.home.ui.HomeScreen
import com.example.musicapp.ui.MiniPlayer
import com.example.musicapp.ui.PlayListSheet
import com.example.playlist.PlayListScreen
import com.example.search.SearchDetailScreen
import com.example.search.SearchScreen
import com.example.ui.theme.MusicAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
               MainScreen()
            }
        }
    }
}

object AppDestinations {
    const val HOME_ROUTE = "home"
    const val ALBUM_LIST_ROUTE = "albumList"
    const val ALBUM_ID_ARG = "albumId"
    const val PLAY_LIST_ROUTE = "playList"
    const val PLAY_LIST_ID_ARG = "playListId"
    const val SEARCH_ROUTE = "search"
    const val SEARCH_KEYWORD_ARG = "keyword"
    const val SEARCH_DETAIL_ROUTE = "searchDetail"
    const val ARTIST_ROUTE = "artist"
    const val ARTIST_ID_ARG = "artistId"
}

@Composable
fun MusicNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = AppDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier=modifier
    ) {
        composable(AppDestinations.HOME_ROUTE) {
            HomeScreen(
                onAlbumClick = { albumId ->
                    navController.navigate("${AppDestinations.ALBUM_LIST_ROUTE}/$albumId")
                },
                onPlayListClick = { playListId ->
                    navController.navigate("${AppDestinations.PLAY_LIST_ROUTE}/$playListId")
                },
                onSearchClick = { navController.navigate(AppDestinations.SEARCH_ROUTE) }
            )
        }
        composable(
            route = "${AppDestinations.ALBUM_LIST_ROUTE}/{${AppDestinations.ALBUM_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.ALBUM_ID_ARG) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getLong(AppDestinations.ALBUM_ID_ARG)
            AlbumListScreen(id = albumId!!)
        }
        composable(
            route = "${AppDestinations.PLAY_LIST_ROUTE}/{${AppDestinations.PLAY_LIST_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.PLAY_LIST_ID_ARG) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val playListId = backStackEntry.arguments?.getLong(AppDestinations.PLAY_LIST_ID_ARG)
            PlayListScreen(id = playListId!!)
        }
        composable(route = AppDestinations.SEARCH_ROUTE) {
            SearchScreen(
                onSearchDetailClick = { keyword -> navController.navigate("${AppDestinations.SEARCH_DETAIL_ROUTE}/$keyword") }
            )
        }
        composable(
            route = "${AppDestinations.SEARCH_DETAIL_ROUTE}/{${AppDestinations.SEARCH_KEYWORD_ARG}}",
            arguments = listOf(navArgument(AppDestinations.SEARCH_KEYWORD_ARG) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString(AppDestinations.SEARCH_KEYWORD_ARG)
            SearchDetailScreen(
                keyword = keyword!!,
                onBack = { navController.popBackStack() },
                onPlayListClick = { id -> navController.navigate("${AppDestinations.PLAY_LIST_ROUTE}/$id") },
                onAlbumClick = { id -> navController.navigate("${AppDestinations.ALBUM_LIST_ROUTE}/$id") },
                onSingerClick = { id -> navController.navigate("${AppDestinations.ARTIST_ROUTE}/$id") }
            )
        }
        composable(
            route = "${AppDestinations.ARTIST_ROUTE}/{${AppDestinations.ARTIST_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.ARTIST_ID_ARG) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getLong(AppDestinations.ARTIST_ID_ARG)
            ArtistScreen(id = artistId!!)
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
){
    val songsData by viewModel.songsList.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showList by remember{mutableStateOf(false)}
    val navController: NavHostController = rememberNavController()
    Scaffold(bottomBar = {MiniPlayer(onShowListClick = {showList=true
    })}) {
            innerPadding-> MusicNavGraph(navController = navController,modifier = Modifier.padding(innerPadding))
    }
    if(showList){
        ModalBottomSheet(
            onDismissRequest = { showList = false },
            sheetState = sheetState
        ) {
            songsData?.let { list ->
                PlayListSheet(
                    items = list,
                    onClick = { index -> viewModel.addMultipleToQueue(index)
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showList = false
                            }
                        }

                    })

            }
        }
    }
}