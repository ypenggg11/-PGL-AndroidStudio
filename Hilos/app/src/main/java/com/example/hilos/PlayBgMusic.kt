package com.example.hilos

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class PlayBgMusic : Service() {

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        Toast.makeText(this@PlayBgMusic,"Service created",Toast.LENGTH_LONG).show()
        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.song)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this@PlayBgMusic,"Service started",Toast.LENGTH_LONG).show()
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this@PlayBgMusic,"Service stopped",Toast.LENGTH_LONG).show()
        mediaPlayer.stop()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}