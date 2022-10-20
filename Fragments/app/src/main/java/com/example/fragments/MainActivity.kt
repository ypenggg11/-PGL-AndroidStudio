package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        //Asignamos el viewBinding para poder acceder a los componentes mediante su variable (viewBinding)
        setContentView(viewBinding.root)

        var seleccion: String? = null

        val fragSpinner = addSpinnerItems()
        seleccion = initFragSpinnerListener(fragSpinner, seleccion)

        initFragsButtonsListeners(seleccion)
    }

    //Cuando seleccionemos un item dentro del spinner, le enviamos al framento 1 (FragmentA())
    //el contenido obtenido del spinner (el seleccionado) como argumento.
    private fun initFragSpinnerListener(fragSpinner: Spinner, seleccion: String?): String? {
        var selec = seleccion

        fragSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Obtenemos el item en concreto, lo ponemos entre comillas para que sea como String
                selec = "${p0!!.getItemAtPosition(p2)}"

                //Creamos una instancia de la clase FragmentA
                val frag1 = FragmentA()
                //Creamos una instancia de la clase Bundle()
                val texto = Bundle()
                //Asignamos el item seleccionado, y le asignamos el 'key' de 'texto'
                //'texto' es la llave definida en FragmentA.kt como nombre del argumento.
                texto.putString("texto", selec)

                //Le pasamos a la instancia del FragmentA, como argumento la instancia del Bundle
                //En nuestra clase Fragment.kt, obtendremos el argumento correspondiente con los keys.
                frag1.arguments = texto

                //Mostramos el fragmento.
                showFragment(frag1)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        return selec
    }

    private fun addSpinnerItems(): Spinner {
        val fragSpinner = viewBinding.fragSpinner

        //Creamos el array adapter con nuestro string-array dentro de strings.xml
        val spinnerArray = ArrayAdapter.createFromResource(
            this,
            R.array.fragText_spinner,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )

        //Asignamos los valores del array adapter obtenido anteriormente a la lista (spinner)
        spinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragSpinner.adapter = spinnerArray

        return fragSpinner
    }

    private fun initFragsButtonsListeners(seleccion: String?) {
        val frag1Button = viewBinding.fragment1Button
        val frag2Button = viewBinding.fragment2Button

        //Cuando presione el botón, cambia al fragmento 1 y le asignamos como texto
        //el item seleccionado en el spinner.
        frag1Button.setOnClickListener {
            val frag1 = FragmentA()

            val texto = Bundle()
            texto.putString("texto", seleccion)
            frag1.arguments = texto

            showFragment(frag1)
        }

        //Cambia al fragmento 2 al presionar el botón.
        frag2Button.setOnClickListener {
            val frag2 = FragmentB()
            showFragment(frag2)
        }
    }

    //Muestra el fragmento en nuestro frame layout
    private fun showFragment(frag: Fragment) {
        //Empezamos la acción
        val fragment = supportFragmentManager.beginTransaction()

        //Reemplazamos el fragmento que está activo en el frame layout indicado como parametro1
        //por el fragmento que le pasemos por el parametro2
        fragment.replace(R.id.fragmentsFrameL, frag)

        //Terminamos la acción
        fragment.commit()
    }
}