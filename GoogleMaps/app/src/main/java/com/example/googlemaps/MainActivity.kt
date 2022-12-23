package com.example.googlemaps

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlemaps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //TODO 3.-
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.googleMapsBtn.setOnClickListener{
            val coord1 = viewBinding.coordinates1EditT
            val coord2 = viewBinding.coordinates2EditT

            val coordinates = "geo:${coord1.text},${coord2.text}"
            val myUri = Uri.parse(coordinates)

            val map = Intent.ACTION_VIEW

            val mapIntent = Intent(map,myUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)

            coord1.text.clear()
            coord2.text.clear()
        }
    }
}