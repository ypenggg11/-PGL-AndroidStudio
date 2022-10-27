package com.example.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.dataClasses.Jugador
import com.example.recyclerview.R

//Clase Adaptador que acepta datos como un Array de objetos Jugador, y un listener para cada Jugador.
//El listener, habrá de definir su contenido al crear una instancia de esta clase.
//Esta clase hereda de RecyclerView.Adapter<Clase ViewHolder (en este caso, el nuestro está interno)>()
class JugadoresAdapter(private val datos: Array<Jugador>, private val clickListener: (Jugador) -> Unit): RecyclerView.Adapter<JugadoresViewHolder>() {

    //Construye (Inflate) un item (un View), con LayoutInflater, desde la vista padre, y construye
    // la vista del hijo (listitem_jugador) como un LinearLayout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listitem_jugador,parent,false) as LinearLayout
        return JugadoresViewHolder(item)
    }

    //Llama al método bindJugador, pasandole el jugador en la posicion de la lista correspondiente.
    //Se ejecuta una vez, por cada Jugador dentro de nuestra lista datos.
    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int) {
        val jugador = datos[position]
        holder.bindJugador(jugador)

        //Tambien, a cada item (View), le asigna un listener a cada uno.
        holder.item.setOnClickListener {clickListener(jugador)}
    }

    //Obtiene la cantidad de items.
    override fun getItemCount(): Int = datos.size
}