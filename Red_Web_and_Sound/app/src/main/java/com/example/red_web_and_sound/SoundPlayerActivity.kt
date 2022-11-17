package com.example.red_web_and_sound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.red_web_and_sound.databinding.ActivitySoundPlayerBinding

class SoundPlayerActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySoundPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySoundPlayerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.back2Button.setOnClickListener {
            val mainIntent = Intent(this@SoundPlayerActivity,MainActivity::class.java)
            startActivity(mainIntent)

            this.finish()
        }
    }
}