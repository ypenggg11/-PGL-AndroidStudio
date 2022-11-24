package com.example.fotovideo

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.fotovideo.databinding.ActivityPhotoBinding
import java.io.IOException

class PhotoActivity : AppCompatActivity() {

    private lateinit var image: ImageView

    companion object {
        val TAKE_PICTURE = 1;
        val SELECT_PICTURE = 2;
        val CAMERA_REQUEST_CODE = 123;
    }

    private lateinit var viewBinding: ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.backB.setOnClickListener {
            val mainIntent = Intent(this@PhotoActivity, MainActivity::class.java)
            startActivity(mainIntent)

            this.finish()
        }

        viewBinding.takePictureB.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this@PhotoActivity,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showMessage("La App tiene los permisos para la camara")
                takePhoto();
            } else {
                ActivityCompat.requestPermissions(
                    this@PhotoActivity, arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            }
        }

        viewBinding.selectImageB.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(galleryIntent, SELECT_PICTURE)
        }
    }

    private fun takePhoto() {
        val photoIntent  =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(photoIntent, TAKE_PICTURE)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this@PhotoActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMessage("Permiso concedido")
                    takePhoto()
                }else{
                    showMessage("Permiso no concedido")
                }
            }else->{
                showMessage("Otro permiso.")
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        image = viewBinding.photoIv

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            val img = data!!.extras?.get("data") as Bitmap
            image.setImageBitmap(img)
        }else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            val uri: Uri? = data!!.data
            try {
                val img = MediaStore.Images.Media.getBitmap(contentResolver,uri)
                image.setImageBitmap(img)
            }catch (_:IOException) {}
        }
    }
}