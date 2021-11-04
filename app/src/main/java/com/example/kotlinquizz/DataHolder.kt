package com.example.kotlinquizz

import com.example.kotlinquizz.models.MainQuestions

object DataHolder {
    var questions: MainQuestions = MainQuestions(arrayListOf())
    var points = 0
    var KEY_GAMES = "games"
}