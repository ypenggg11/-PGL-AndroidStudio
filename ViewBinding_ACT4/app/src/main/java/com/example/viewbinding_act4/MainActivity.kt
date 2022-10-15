package com.example.viewbinding_act4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.viewbinding_act4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Necesario poner esto dentro de build.gradle -> android {aquí}
    //Para usar viewBinding, que es otra manera de buscar por id -> findViewById
    /*viewBinding{
                enabled = true;
            }*/
    //Declaramos una variable de tipo ActivityMainBinding y tipo lateinit para iniciarlo más tarde
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicializamos la variable
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        //Mostramos la vista raiz
        setContentView(viewBinding.root)

        //Establecemos los listener de los componentes
        proLangSpinnerListener()
        exitButtonListener()
    }

    private fun proLangSpinnerListener() {
        val proLangSpinner = viewBinding.proLangSpinner
        val selectedTextV = viewBinding.selectedTextV

        //Crea el array adapter a partir del array que creamos en strings.xml (string-array)
        //Opción gráfico: Spinner -> Common Attributes -> Entries -> @array/proLang
        val proLang = ArrayAdapter.createFromResource(
            this,
            R.array.proLang,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )

        //Asignamos los valores del array adapter obtenido anteriormente a la lista (spinner)
        proLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        proLangSpinner.adapter = proLang

        //Depende del item seleccionado en el spinner, ejecutará distintas acciones
        proLangSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //Cuando esté seleccionado...
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedTextV.text = "Has seleccionado ${p0!!.getItemAtPosition(p2)}"
            }

            //Cuando no haya nada seleccionado...
            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedTextV.text = "No has seleccionado ningún lenguaje"
            }

        }
    }

    private fun exitButtonListener() {
        val exitButton = viewBinding.exitButton

        exitButton.setOnClickListener {
            //Cierra el activity.
            finish()
        }
    }
}