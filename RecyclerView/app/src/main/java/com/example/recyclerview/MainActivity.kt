package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.adapter.JugadoresAdapter
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.dataClasses.Jugador

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = viewBinding.recyclerView

        //Asigna un layout al recyclerView (con layoutManager), en este caso, le asigna un
        //LinearLayout en este contexto, un LinearLayout vertical.
       recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Ejemplo de asignar un GridLayout
        recyclerView.layoutManager = GridLayoutManager(this,4)

        //Creamos un array de 30 objetos de clase Jugador
        val datos = Array(30) { it -> Jugador("Jugador $it", it) }

        //Crea una instancia del objeto Adaptador, pasandole los datos, y
        //el código que ejecuta el listener (clickListener)
        val adaptador = JugadoresAdapter(datos) {
            Toast.makeText(this, "Has pulsado el -> ${it.nombre}", Toast.LENGTH_LONG).show()
        }

        //Asigna al recyclerView, nuestra clase Adaptador
        recyclerView.adapter = adaptador

        //Añade una decoración para cada item, en este caso, un divider vertical.
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}