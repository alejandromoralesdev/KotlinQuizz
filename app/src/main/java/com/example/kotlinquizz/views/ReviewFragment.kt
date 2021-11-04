package com.example.kotlinquizz.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.kotlinquizz.DataHolder
import com.example.kotlinquizz.HomeActivity
import com.example.kotlinquizz.R
import com.example.kotlinquizz.models.Game
import io.paperdb.Paper
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class ReviewFragment : Fragment() {
    val DBName = "KotlinQuizz"
    val USERNAME = "username"

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.tvTitle)
        val emoji = view.findViewById<TextView>(R.id.tvEmojis)
        val btnPlay = view.findViewById<Button>(R.id.btPlayAgain)
        val btnScore = view.findViewById<Button>(R.id.btScore)
        val btnChangeUser = view.findViewById<Button>(R.id.btChangeUser)
        val viewKonfetti = view.findViewById<KonfettiView>(R.id.viewKonfetti)

        val points = DataHolder.points
        sharedPref = requireActivity().getSharedPreferences(DBName, Context.MODE_PRIVATE)

        val nombre = sharedPref.getString(USERNAME, "")

        if(points > 20000) {
            title.text = "¡Lo has hecho muy bien ${nombre}!\nHas conseguido ${points} puntos"
            emoji.text = "\uD83D\uDD25 \uD83D\uDE0E \uD83D\uDC4F"
            showKonfetti(viewKonfetti)
        } else {
            title.text = "¡Qué lástima ${nombre}!\nSolo has sacado ${points} puntos"
            emoji.text = "\uD83D\uDE14 \uD83D\uDCA9"
        }

        btnPlay.setOnClickListener {
            Log.wtf("DALE","HELO")
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framelayout, QuestionFragment()).commit()
        }


        btnChangeUser.setOnClickListener {
            sharedPref.edit().remove(USERNAME).apply()
            requireActivity().startActivity(Intent(context, HomeActivity::class.java))
            requireActivity().finish()
        }

        //Paper.book().destroy()

        // LEEMOS UN ARRAYLIST DE PARTIDAS, Y EN CASO DE QUE NO HAYA NINGUNA, QUE LA VARIABLE "misPartidas" sea un array vacio
        val misPartidas = Paper.book().read<ArrayList<Game>>(DataHolder.KEY_GAMES, arrayListOf())
        misPartidas.add(Game(nombre ?: "", points))
        Paper.book().write(DataHolder.KEY_GAMES, misPartidas)

        Log.v("DALE", "Datos $misPartidas")

        btnScore.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framelayout, ScoresFragment()).commit()
        }
    }



    fun showKonfetti(viewKonfetti: KonfettiView) {
        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }
}