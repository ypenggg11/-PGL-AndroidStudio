package com.example.fotovideo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import com.example.fotovideo.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityVideoBinding
    private lateinit var videoView: VideoView

    companion object{
        const val CAPTURE_VIDEO_CODE = 2
        const val CAMERA_REQUEST_CODE = 123;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.backBtn.setOnClickListener {
            val mainIntent = Intent(this@VideoActivity,MainActivity::class.java)
            startActivity(mainIntent)

            this.finish()
        }

        viewBinding.recordVideoB.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this@VideoActivity,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                recordVideo()
            } else {
                ActivityCompat.requestPermissions(
                    this@VideoActivity, arrayOf(android.Manifest.permission.CAMERA),
                    VideoActivity.CAMERA_REQUEST_CODE
                )
            }
        }
    }

    private fun recordVideo() {
        videoView = viewBinding.videoView

        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(videoIntent, CAPTURE_VIDEO_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PhotoActivity.CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recordVideo()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAPTURE_VIDEO_CODE && resultCode == Activity.RESULT_OK) {
            val videoUri = data!!.data
            videoView.setVideoURI(videoUri)
            videoView.setMediaController(MediaController(this))
            videoView.requestFocus()
            videoView.start()
        }
    }
}