package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        welcomeMessage()

        setListeners()
    }

    private fun welcomeMessage() {
        val username:String? = getBundleByKey("username")

        findViewById<TextView>(R.id.welcomeTextView).text = "Welcome $username! "
        //        findViewById<TextView>(R.id.welcomeTextView).text = "Your password is: $password "
    }

    private fun getBundleByKey(key: String): String? {
        val bundle = intent.extras
        //        val password = bundle?.getString("password")

        return bundle?.getString(key)
    }


    private fun setListeners() {
        val exitButton = findViewById<Button>(R.id.exitButton)
        exitButton.setOnClickListener {
            exitButtonListener()
        }
    }

    private fun exitButtonListener() {
        val loginIntent = Intent(this@Welcome, MainActivity::class.java)
        startActivity(loginIntent)
    }
}