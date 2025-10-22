package com.example.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.albumList.AlbumListScreen
import com.example.home.ui.HomeScreen
import com.example.playlist.PlayListScreen
import com.example.search.SearchDetailScreen
import com.example.search.SearchScreen
import com.example.ui.theme.MusicAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                val navController:NavHostController= rememberNavController()
                MusicNavGraph(navController=navController)
            }
        }
    }
}

object AppDestinations{
    const val HOME_ROUTE="home"
    const val ALBUM_LIST_ROUTE="albumList"
    const val ALBUM_ID_ARG = "albumId"
    const val PLAY_LIST_ROUTE="playList"
    const val PLAY_LIST_ID_ARG="playListId"
    const val SEARCH_ROUTE="search"
    const val SEARCH_KEYWORD_ARG="keyword"
    const val SEARCH_DETAIL_ROUTE="searchDetail"
}

@Composable
fun MusicNavGraph(
    navController: NavHostController,
    startDestination:String=AppDestinations.HOME_ROUTE
){
    NavHost(navController=navController,
        startDestination=startDestination){
        composable(AppDestinations.HOME_ROUTE){
            HomeScreen(onAlbumClick = { albumId ->
                navController.navigate("${AppDestinations.ALBUM_LIST_ROUTE}/$albumId")
            },
                onPlayListClick = { playListId ->
                    navController.navigate("${AppDestinations.PLAY_LIST_ROUTE}/$playListId")
                },
                onSearchClick = {navController.navigate(AppDestinations.SEARCH_ROUTE)}
                )
        }
        composable(route = "${AppDestinations.ALBUM_LIST_ROUTE}/{${AppDestinations.ALBUM_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.ALBUM_ID_ARG){type= NavType.LongType})
            ){backStackEntry->
            val albumId=backStackEntry.arguments?.getLong(AppDestinations.ALBUM_ID_ARG)
            AlbumListScreen(id=albumId!!)
    }
        composable(route="${AppDestinations.PLAY_LIST_ROUTE}/{${AppDestinations.PLAY_LIST_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.PLAY_LIST_ID_ARG){type= NavType.LongType})
){
            backStackEntry->
            val playListId=backStackEntry.arguments?.getLong(AppDestinations.PLAY_LIST_ID_ARG)
            PlayListScreen(id=playListId!!)
        }
        composable(route=AppDestinations.SEARCH_ROUTE){
            SearchScreen(
                onSearchDetailClick = {keyword -> navController.navigate("${AppDestinations.SEARCH_DETAIL_ROUTE}/$keyword")}
            )
        }
        composable(route="${AppDestinations.SEARCH_DETAIL_ROUTE}/{${AppDestinations.SEARCH_KEYWORD_ARG}}",
            arguments = listOf(navArgument(AppDestinations.SEARCH_KEYWORD_ARG){type= NavType.StringType})
        ) {
            backStackEntry ->
            val keyword=backStackEntry.arguments?.getString(AppDestinations.SEARCH_KEYWORD_ARG)
            SearchDetailScreen(keyword = keyword!!, onBack = {navController.popBackStack()}, onPlayListClick = {id->navController.navigate("${AppDestinations.PLAY_LIST_ROUTE}/$id")})
        }
}}
