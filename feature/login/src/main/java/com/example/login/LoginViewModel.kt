package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.manager.UserSessionManager
import com.example.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginStatus {
    IDLE,
    LOADING,
    WAITING_SCAN,
    SCANNED,
    EXPIRED,
    SUCCESS,
    ERROR
}

data class LoginUiState(
    val status: LoginStatus = LoginStatus.IDLE,
    val qrImageUrl: String = "",
    val qrUrl: String = "",
    val key: String = "",
    val message: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: AuthRepository,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    fun startLogin() {
        viewModelScope.launch {
            _uiState.value = LoginUiState(status = LoginStatus.LOADING)
            getQrCodeAndPoll()
        }
    }

    private suspend fun getQrCodeAndPoll() {
        try {
            val keyData = loginRepository.getKey()
            val key = keyData.data.unikey
            if (keyData.code != 200) {
                _uiState.value = LoginUiState(status = LoginStatus.ERROR, message = "获取密钥失败")
                return
            }

            val loginPic = loginRepository.getPic(key)
            if (loginPic.code != 200) {
                _uiState.value = LoginUiState(status = LoginStatus.ERROR, message = "获取二维码失败")
                return
            }

            _uiState.value = LoginUiState(
                status = LoginStatus.WAITING_SCAN,
                qrImageUrl = loginPic.data.qrimg,
                qrUrl = loginPic.data.qrurl,
                key = key,
                message = "请使用网易云音乐App扫码登录"
            )

            // 开始轮询扫码状态
            pollLoginStatus(key)

        } catch (e: Exception) {
            _uiState.value = LoginUiState(
                status = LoginStatus.ERROR,
                message = "网络错误: ${e.message}"
            )
        }
    }

    private suspend fun pollLoginStatus(key: String, useNoCookie: Boolean = false) {
        while (true) {
            delay(2000) // 每2秒轮询一次

            try {
                val statue = loginRepository.getStatue(key, useNoCookie)
                when (statue.code) {
                    803 -> {
                        // 授权登录成功！803 状态码下返回 cookies
                        userSessionManager.saveCookie(statue.cookie)
                        _uiState.value = _uiState.value.copy(
                            status = LoginStatus.SUCCESS,
                            message = "登录成功"
                        )
                        _loginSuccess.value = true
                        break
                    }
                    802 -> {
                        // 已扫码但未确认
                        _uiState.value = _uiState.value.copy(
                            status = LoginStatus.SCANNED,
                            message = "已扫码，请在手机上确认登录"
                        )
                    }
                    801 -> {
                        // 等待扫码
                        _uiState.value = _uiState.value.copy(
                            status = LoginStatus.WAITING_SCAN,
                            message = "请使用网易云音乐App扫码登录"
                        )
                    }
                    800 -> {
                        // 二维码已过期，重新获取
                        _uiState.value = _uiState.value.copy(
                            status = LoginStatus.EXPIRED,
                            message = "二维码已过期，正在刷新..."
                        )
                        delay(1000)
                        getQrCodeAndPoll()
                        break
                    }
                    502 -> {
                        // 502 错误，加上 noCookie 参数重试
                        if (!useNoCookie) {
                            _uiState.value = _uiState.value.copy(
                                status = LoginStatus.WAITING_SCAN,
                                message = "重试中..."
                            )
                            pollLoginStatus(key, useNoCookie = true)
                        } else {
                            _uiState.value = _uiState.value.copy(
                                status = LoginStatus.ERROR,
                                message = "登录失败，请重试"
                            )
                        }
                        break
                    }
                    else -> {
                        _uiState.value = _uiState.value.copy(
                            status = LoginStatus.ERROR,
                            message = statue.message
                        )
                        break
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    status = LoginStatus.ERROR,
                    message = "网络错误: ${e.message}"
                )
                break
            }
        }
    }

    fun refreshQrCode() {
        startLogin()
    }

    fun visitorLogin() {
        viewModelScope.launch {
            try {
                _uiState.value = LoginUiState(status = LoginStatus.LOADING, message = "游客登录中...")
                loginRepository.VisitorLogin()
                _uiState.value = LoginUiState(status = LoginStatus.SUCCESS, message = "游客登录成功")
                _loginSuccess.value = true
            } catch (e: Exception) {
                _uiState.value = LoginUiState(
                    status = LoginStatus.ERROR,
                    message = "游客登录失败: ${e.message}"
                )
            }
        }
    }
}
