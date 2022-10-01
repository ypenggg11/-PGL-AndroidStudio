package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val user = "admin"
    private val pass = "Dam1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    private fun setListeners() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            loginButtonListener()
        }
    }

    private fun loginButtonListener() {
        val username = findViewById<EditText>(R.id.usernameEditText).text.toString()
        //            val password = findViewById<EditText>(R.id.passwordEditText).text.toString()

        if (username == user) {
            if (findViewById<EditText>(R.id.passwordEditText).text.toString() == pass) {
                val welcomeIntent = Intent(this@MainActivity, Welcome::class.java)

                welcomeIntent.putExtra("username", username)
                //                    welcomeIntent.putExtra("password",password)

                startActivity(welcomeIntent)
            } else {
                showToast("Wrong password!")
            }
        } else {
            showToast("Invalid username!")
        }
    }

    private fun showToast(textToShow:String) {
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, textToShow, duration)
        toast.show()

        //Toast.makeText(applicationContext, textToShow, duration).show()
    }
}