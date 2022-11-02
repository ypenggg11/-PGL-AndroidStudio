package com.example.sharedpreferences_y_ficheros

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.sharedpreferences_y_ficheros.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private val preferencesName = "email_data"
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var preferencesEditor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initPreferences()
        initListeners()
    }

    private fun initPreferences() {
        sharedPreferences = getSharedPreferences(preferencesName, MODE_PRIVATE)
        preferencesEditor = sharedPreferences.edit()
    }

    private fun initListeners() {
        val emailEditT = viewBinding.emailEditT
        val datosEditT = viewBinding.datosEditT

        viewBinding.grabarButton.setOnClickListener {
            savePreferences(emailEditT, datosEditT, preferencesEditor)
        }

        viewBinding.recuperarButton.setOnClickListener {
            recoverPreferences(sharedPreferences, emailEditT, datosEditT)
        }
    }

    private fun recoverPreferences(
        preferences: SharedPreferences,
        keyField: EditText,
        dataField: EditText
    ) {
        val data = preferences.getString(keyField.text.toString(), "")

        if (data != null) {
            if (data.isEmpty()) {
                showToast("No existe datos asociado a dicho mail.")
            } else {
                dataField.setText(data)
            }
        }
    }

    private fun savePreferences(
        keyField: EditText,
        dataField: EditText,
        editor: SharedPreferences.Editor
    ) {
        if (keyField.text.trim().isNotEmpty() && dataField.text.trim().isNotEmpty()) {
            editor.putString(keyField.text.toString(), dataField.text.toString())
            editor.apply()
            //editor.commit()

            showToast("Datos guardados")

            keyField.setText("")
            dataField.setText("")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}