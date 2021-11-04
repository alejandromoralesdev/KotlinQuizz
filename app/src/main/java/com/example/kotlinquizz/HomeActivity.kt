package com.example.kotlinquizz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ViewAnimator
import com.example.kotlinquizz.databinding.ActivityHomeBinding
import com.example.kotlinquizz.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    val dbName = "KotlinQuizz"
    val USERNAME = "username"
    val tag = "HomeActivity"

    private lateinit var b: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        val sharedPref: SharedPreferences = getSharedPreferences(dbName, Context.MODE_PRIVATE)
        /*val edit = sharedPref.edit()
        edit.putString(userName, "Alejandro")
        edit.apply()*/

        var userName = sharedPref.getString(USERNAME, null)

        Log.v(tag, "El nombre de usuario es $userName")

        if (!userName.isNullOrEmpty()) {
            showViews()
            b.tvUserName.text = getString(R.string.welcome,userName)
        }

        b.btContinue.setOnClickListener {
            val name = b.etName.text.toString()
            saveName(name, sharedPref, userName)
            if(checkName(name,userName)){
                showViews()
            }
        }

        b.btChangeUser.setOnClickListener {
            showNameInput()
        }

        b.btStart.setOnClickListener {
            val intent = Intent(this, QuizzActivity::class.java)
            startActivity(intent)
        }
    }

    fun saveName(name: String, sharedPref: SharedPreferences, userName: String?) {
        if (name.length > 3 && name != userName) {
            val edit = sharedPref.edit()
            edit.putString(USERNAME, name)
            edit.apply()
            b.tvUserName.text = getString(R.string.welcome,sharedPref.getString(USERNAME, null))
        }
    }

    fun checkName(name: String, userName: String?): Boolean{
        if(name == userName){
            Toast.makeText(this, "El usuario introducido es el mismo!", Toast.LENGTH_LONG).show()
            return false
        }else if(name.length <= 3){
            Toast.makeText(this, "Introduce un nombre más largo", Toast.LENGTH_LONG).show()
            return false
        }else{
            return true
        }

    }

    fun showViews() {
        b.svName.visibility = View.GONE
        b.lvChangeUser.visibility = View.VISIBLE
    }

    fun showNameInput() {
        b.svName.visibility = View.VISIBLE
        b.lvChangeUser.visibility = View.GONE
    }
}