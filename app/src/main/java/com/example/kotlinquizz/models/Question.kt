package com.example.kotlinquizz.models

data class Question(
    val answers: List<Answer>,
    val title: String
)