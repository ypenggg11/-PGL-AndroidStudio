package com.example.repasout3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.repasout3.databinding.ActivityMainBinding

/* USED: MENU */
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
            R.id.camera -> {
                startActivity(Intent(this@MainActivity , CameraActivity::class.java))
                finish()
            }

            R.id.internet -> {
                startActivity(Intent(this@MainActivity , InternetActivity::class.java))
                finish()
            }

            R.id.map -> {
                startActivity(Intent(this@MainActivity , MapsActivity::class.java))
                finish()
            }

            R.id.thread -> {
                //TODO Map not working
                startActivity(Intent(this@MainActivity , ThreadActivity::class.java))
                finish()
            }

            R.id.firestore -> {
                //TODO Not implemented yet
                startActivity(Intent(this@MainActivity , FireBaseActivity::class.java))
                finish()
            }
        }

        return true
    }
}