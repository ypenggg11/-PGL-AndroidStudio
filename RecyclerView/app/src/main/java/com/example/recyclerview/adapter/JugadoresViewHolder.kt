package com.example.recyclerview.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.dataClasses.Jugador
import com.example.recyclerview.R

//Clase ViewHolder, que necesitará nuestra clase Adaptador
//Esta clase hereda de RecyclerView.ViewHolder(vista de cada item (View))
class JugadoresViewHolder(val item: View) : RecyclerView.ViewHolder(item) {

    //Busca en la vista (View) pasado como parámetro, el componente por Id.
    val nombreTextV = item.findViewById<TextView>(R.id.nombreTextV)
    val edadTextView = item.findViewById<TextView>(R.id.edadTextV)

    //Relaciona nuestros datos (lista de Jugador) con la vista.
    //(Los componentes que cargamos en el ViewHolder)
    fun bindJugador(jugador: Jugador) {
        nombreTextV.text = jugador.nombre
        edadTextView.text = jugador.edad.toString()
    }
}