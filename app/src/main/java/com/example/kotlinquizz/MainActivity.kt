package com.example.kotlinquizz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.example.kotlinquizz.databinding.ActivityMainBinding
import com.example.kotlinquizz.models.MainQuestions
import com.google.gson.Gson
import com.rommansabbir.animationx.*
import io.paperdb.Paper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        Paper.init(this)

        DataHolder.questions = readQuiz("questions.json")

        appear()
        disappear()
        goHome()
    }

    fun readQuiz(routeJSON: String): MainQuestions {
        var json = ""
        try {
            val url = routeJSON
            val bufferedReader = BufferedReader(
                InputStreamReader(assets?.open(url))
            )
            val paramsBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                paramsBuilder.append(line)
                line = bufferedReader.readLine()
            }
            json = paramsBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val data = Gson().fromJson(json, MainQuestions::class.java)
        return data
    }

    fun appear() {
        b.ivQuizzLogo.animationXAttention(Attention.ATTENTION_TA_DA)
        //b.ivQuizzLogo.animationXRotate(Rotate.ROTATE_IN)
    }

    fun disappear() {
        Handler(Looper.getMainLooper()).postDelayed({
            b.ivQuizzLogo.animationXAttention(Fade.FADE_OUT)
        }, 1500)
    }

    fun goHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000)
    }
}