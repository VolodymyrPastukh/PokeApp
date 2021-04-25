package com.vovan.pokeapp.presentation.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel : ViewModel() {

    private val _isStartPlay = MutableLiveData<Boolean>()
    val isStartPlay: LiveData<Boolean>
        get() = _isStartPlay


    fun startPlay(){
        _isStartPlay.value = true
    }

    fun resetPLay(){
        _isStartPlay.value = false
    }
}