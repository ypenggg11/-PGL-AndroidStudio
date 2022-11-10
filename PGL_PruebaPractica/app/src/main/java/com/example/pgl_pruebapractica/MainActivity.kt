package com.example.pgl_pruebapractica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pgl_pruebapractica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val USER = "root"
    private val PASSWORD = "Toor4567"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initListeners()
    }

    private fun initListeners() {
        viewBinding.loginButton.setOnClickListener {
            if (USER == viewBinding.usernameEditT.text.toString()) {
                if (viewBinding.passwordEditT.text.toString() == PASSWORD) {
                    val databaseIntent = Intent(this@MainActivity, DataBaseActivity::class.java)

                    startActivity(databaseIntent)

                    finish()
                } else {
                    showToast("Wrong password!")
                }
            } else {
                showToast("Invalid username!")
            }
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show()
    }
}