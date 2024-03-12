package com.hfad.guessing_game

import androidx.lifecycle.ViewModel

class ResultViewModel(finalResult: String) : ViewModel(){
    val result = finalResult
}