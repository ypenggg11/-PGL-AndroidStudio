package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorJugadores(private val datos: Array<Jugador>, private val clickListener: (Jugador) -> Unit): RecyclerView.Adapter<AdaptadorJugadores.JugadoresViewHolder>() {

    class JugadoresViewHolder(val item: View): RecyclerView.ViewHolder(item) {
        val nombreTextV = item.findViewById<TextView>(R.id.nombreTextV)
        val edadTextView = item.findViewById<TextView>(R.id.edadTextV)

        fun bindJugador(jugador: Jugador) {
            nombreTextV.text = jugador.nombre
            edadTextView.text = jugador.edad.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listitem_jugador,parent,false) as LinearLayout
        return JugadoresViewHolder(item)
    }

    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int) {
        val jugador = datos[position]
        holder.bindJugador(jugador)

        holder.item.setOnClickListener {clickListener(jugador)}
    }

    override fun getItemCount(): Int = datos.size
}