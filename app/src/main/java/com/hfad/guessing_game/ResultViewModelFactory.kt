package com.hfad.guessing_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModelFactory(private val finalResult: String)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))
            return ResultViewModel(finalResult) as T

        throw IllegalArgumentException("unknown ViewModel")
    }
    /*fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))
            return ResultViewModel(finalResult) as T

        throw IllegalArgumentException("unknown ViewModel")
    }*/
}