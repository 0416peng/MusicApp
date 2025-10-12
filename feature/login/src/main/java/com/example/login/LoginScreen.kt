package com.example.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val uriHandler= LocalUriHandler.current
    val url by viewModel.url.collectAsState()
    LaunchedEffect(url) {
        val currentUrl = url
        if (currentUrl.isNotEmpty()) {
            uriHandler.openUri(currentUrl)
        }
    }
    Column(modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
        Button(onClick = {viewModel.login()}) {
            Text("二维码登录")
        }//暂时无法使用
        Button(onClick = {viewModel.visitorLogin()}) {
            Text("游客登录")
        }
    }
}
