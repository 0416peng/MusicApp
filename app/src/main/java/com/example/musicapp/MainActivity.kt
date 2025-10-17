package com.example.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.login.LoginViewModel
import com.example.ui.theme.MusicAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.home.HomeScreen
import com.example.login.LoginScreen
import com.example.ui.AlbumList.AlbumListScreen


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
            })
        }
        composable(route = "${AppDestinations.ALBUM_LIST_ROUTE}/{${AppDestinations.ALBUM_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.ALBUM_ID_ARG){type= NavType.LongType})
            ){backStackEntry->
            val albumId=backStackEntry.arguments?.getLong(AppDestinations.ALBUM_ID_ARG)
            AlbumListScreen(id=albumId!!)
    }
}}
