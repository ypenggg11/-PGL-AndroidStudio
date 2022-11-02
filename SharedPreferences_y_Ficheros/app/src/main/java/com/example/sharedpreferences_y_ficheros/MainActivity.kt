package com.example.sharedpreferences_y_ficheros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferences_y_ficheros.databinding.ActivityMainBinding

private lateinit var viewBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


    }
}