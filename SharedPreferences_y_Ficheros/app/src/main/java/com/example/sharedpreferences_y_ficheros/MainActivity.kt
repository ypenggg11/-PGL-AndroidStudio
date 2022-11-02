package com.example.sharedpreferences_y_ficheros

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharedpreferences_y_ficheros.databinding.ActivityMainBinding

private lateinit var viewBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val preferences = getSharedPreferences("email_data", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        val grabarButton = viewBinding.grabarButton
        val recuperarButton = viewBinding.recuperarButton

        grabarButton.setOnClickListener {
            val emailEditT = viewBinding.emailEditT
            val datosEditT = viewBinding.datosEditT

            if (emailEditT.text.isNotEmpty()&&datosEditT.text.isNotEmpty()){
                editor.putString(emailEditT.text.toString(),datosEditT.text.toString())
                editor.apply()

                Toast.makeText(this,"Datos guardados",Toast.LENGTH_LONG).show()

                emailEditT.setText("")
                datosEditT.setText("")
            }
        }


    }
}