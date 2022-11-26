package com.example.fotovideo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fotovideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.photoB.setOnClickListener {
            val photoIntent = Intent(this@MainActivity, PhotoActivity::class.java)
            startActivity(photoIntent)

            this.finish()
        }

        viewBinding.playB.setOnClickListener {
            val videoIntent = Intent(this@MainActivity,VideoActivity::class.java)
            startActivity(videoIntent)

            this.finish()
        }

        viewBinding.offB.setOnClickListener {
            this.finish()
        }
    }
}