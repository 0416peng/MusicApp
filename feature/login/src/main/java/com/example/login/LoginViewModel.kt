package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: AuthRepository
) : ViewModel() {
    private val _url = MutableStateFlow<String>("")
    val url = _url.asStateFlow()
    fun login() {
        viewModelScope.launch {
            val keyData = loginRepository.getKey()
            val key = keyData.data.unikey
            if (keyData.code == 200) {
                val loginPic = loginRepository.getPic(key)
                if (loginPic.code == 200) {
                    _url.value = loginPic.data.qrurl
                }
            }
        }

    }

    fun visitorLogin() {
        viewModelScope.launch {
            val data = loginRepository.VisitorLogin()
        }
    }
}