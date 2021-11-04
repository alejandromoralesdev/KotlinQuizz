package com.example.kotlinquizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinquizz.views.QuestionFragment

class QuizzActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz)

        supportFragmentManager.beginTransaction().replace(R.id.framelayout, QuestionFragment()).commit()
    }
}