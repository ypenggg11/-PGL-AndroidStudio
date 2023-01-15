package com.example.repasout3

import android.Manifest
//noinspection SuspiciousImport
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.repasout3.databinding.ActivityCamera2Binding


/* USED: CAMERA FOR TAKING A PICTURE (VIDEO USED IN ANOTHER PROJECT) */
class CameraActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityCamera2Binding
    private lateinit var image: ImageView

    companion object {
        val TAKE_PICTURE = 1
        val CAMERA_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCamera2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListener()
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this@CameraActivity, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListener() {
        /* If CAMERA permission was granted, starts an image capture activity,
        *  else request the CAMERA permission */
        viewBinding.cameraBtn.setOnClickListener {
            if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                ==
                PackageManager.PERMISSION_GRANTED
            ) {
                val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(photoIntent, TAKE_PICTURE)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            }
        }
    }

    /* When .requestPermissions() method it's called, it will run this method */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(photoIntent, TAKE_PICTURE)
                } else {
                    //Show toast (not granted)
                }
            }
            else -> {
                //Show toast (another permission)
            }
        }
    }

    /* Called when startActivityForResult() it's executed */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        image = viewBinding.imageView

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            val img = data!!.extras?.get("data") as Bitmap

            image.setImageBitmap(img)
            viewBinding.imageTitleTv.text = "Image preview"
        }
    }
}