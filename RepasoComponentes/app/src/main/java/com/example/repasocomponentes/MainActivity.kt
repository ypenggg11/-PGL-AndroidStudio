package com.example.repasocomponentes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.repasocomponentes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val lobbyFrag = Lobby()
        changeFragment(lobbyFrag)

        initListeners()
    }

    private fun initListeners() {
        val lobbyButton = viewBinding.lobbyButton
        val aboutMeButton = viewBinding.aboutMeButton

        lobbyButton.setOnClickListener {
            val lobbyFrag = Lobby()
            changeFragment(lobbyFrag)
        }

        aboutMeButton.setOnClickListener {
            val aboutMeFrag = AboutMe()
            changeFragment(aboutMeFrag)
        }
    }

    private fun changeFragment(frag: Fragment) {
        val currentFragment = supportFragmentManager.beginTransaction()
        currentFragment.replace(R.id.frameLayout, frag)
        currentFragment.commit()
    }
}