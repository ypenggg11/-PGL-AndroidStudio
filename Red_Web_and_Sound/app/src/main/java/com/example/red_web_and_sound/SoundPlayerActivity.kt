package com.example.red_web_and_sound

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.red_web_and_sound.databinding.ActivitySoundPlayerBinding

class SoundPlayerActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySoundPlayerBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySoundPlayerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var position = 0

        mediaPlayer = MediaPlayer.create(this@SoundPlayerActivity, R.raw.song)

        viewBinding.back2Button.setOnClickListener {
            val mainIntent = Intent(this@SoundPlayerActivity, MainActivity::class.java)
            startActivity(mainIntent)

            this.finish()
        }

        viewBinding.playButton.setOnClickListener {
            mediaPlayer.start()
        }

        viewBinding.pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                position = mediaPlayer.currentPosition
                mediaPlayer.pause()
            }
        }

        viewBinding.stopButton.setOnClickListener {
            mediaPlayer.pause()
            position = 0
            mediaPlayer.seekTo(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}