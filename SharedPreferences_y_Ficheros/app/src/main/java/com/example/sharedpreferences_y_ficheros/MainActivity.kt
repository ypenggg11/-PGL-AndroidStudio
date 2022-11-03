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

    /**
     * Inicializa nuestro SharedPreferences con un nombre y un modo.
     * Luego, inicializa el editor de nuestro SharedPreferences.
     */
    private fun initPreferences() {
        sharedPreferences = getSharedPreferences(preferencesName, MODE_PRIVATE)
        preferencesEditor = sharedPreferences.edit()
    }

    /**
     * Inicia los listeners de nuestros componentes.
     */
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

    /**
     * Recupera los datos de un preference a partir de una llave.
     * @param keyField Campo de la llave del preference.
     * @param dataField Campo de los datos del preference.
     * @param preferences Un SharedPreferences
     */
    private fun recoverPreferences(
        preferences: SharedPreferences,
        keyField: EditText,
        dataField: EditText
    ) {
        //Obtenemos los datos a partir de una llave.
        val data = preferences.getString(keyField.text.toString(), "")

        //Comprueba si devuelve algo (Si devuelve, muestra los lados, sino, otro mensaje)
        if (data != null) {
            if (data.isEmpty()) {
                showToast("No existe datos asociado a dicho mail.")
            } else {
                dataField.setText(data)
            }
        }
    }

    /**
     * Guarda los datos de un preference a partir de una llave.
     * @param keyField Campo de la llave del preference.
     * @param dataField Campo de los datos del preference.
     * @param editor Editor del SharedPreferences
     */
    private fun savePreferences(
        keyField: EditText,
        dataField: EditText,
        editor: SharedPreferences.Editor
    ) {
        //Comprueba si los campos no están vacíos.
        if (keyField.text.trim().isNotEmpty() && dataField.text.trim().isNotEmpty()) {

            //Ya que acepta emails, comprueba si contiene el '@'
            if (keyField.text.toString().contains("@")) {
                //Almacena en el editor del preferences, un clave y un valor asociado a esa clave.
                editor.putString(keyField.text.toString(), dataField.text.toString())
                //Aplicamos los cambios, ya sea con .apply() o .commit()
                editor.apply()
                //editor.commit()

                showToast("Datos guardados")

                keyField.setText("")
                dataField.setText("")
            }
        }
    }

    /**
     * Muestra un Toast con un mensaje dado.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}