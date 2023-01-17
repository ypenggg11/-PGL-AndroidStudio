package com.example.repasout3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.repasout3.databinding.ActivityFireBaseBinding

class FireBaseActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFireBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFireBaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


    }
}