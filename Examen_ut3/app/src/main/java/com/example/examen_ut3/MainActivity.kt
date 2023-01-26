package com.example.examen_ut3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.examen_ut3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    /* Menu implementation */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /* Menu click listener */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navegador_item -> {
                startActivity(Intent(this@MainActivity , NavegadorActivity::class.java))
                finish()
            }

            R.id.thread_item -> {
                startActivity(Intent(this@MainActivity , HilosActivity::class.java))
                finish()
            }

            R.id.firebase_item -> {
                startActivity(Intent(this@MainActivity , FirebaseActivity::class.java))
                finish()
            }
        }

        return true
    }
}