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

        val emailEditT = viewBinding.emailEditT
        val datosEditT = viewBinding.datosEditT

        grabarButton.setOnClickListener {

            if (emailEditT.text.trim().isNotEmpty() && datosEditT.text.trim().isNotEmpty()) {
                editor.putString(emailEditT.text.toString(), datosEditT.text.toString())
                editor.apply()

                showToast("Datos guardados")

                emailEditT.setText("")
                datosEditT.setText("")
            }
        }

        recuperarButton.setOnClickListener {
            val data = preferences.getString(emailEditT.text.toString(), "")

            if (data != null) {
                if (data.isEmpty()) {
                    showToast("No existe datos asociado a dicho mail.")
                } else {
                    datosEditT.setText(data)
                }
            }
        }
    }

    private fun showToast(message:String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}