package com.example.playlist

import androidx.lifecycle.ViewModel
import com.example.data.repository.playList.PlayListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val playListRepository: PlayListRepository
) : ViewModel() {

}