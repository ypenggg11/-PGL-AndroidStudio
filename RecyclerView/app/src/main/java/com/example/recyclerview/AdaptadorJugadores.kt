package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Clase Adaptador que acepta datos como un Array de objetos Jugador, y un listener para cada Jugador.
//El listener, habrá de definir su contenido al crear una instancia de esta clase.
//Esta clase hereda de RecyclerView.Adapter<Clase ViewHolder (en este caso, el nuestro está interno)>()
class AdaptadorJugadores(private val datos: Array<Jugador>, private val clickListener: (Jugador) -> Unit): RecyclerView.Adapter<AdaptadorJugadores.JugadoresViewHolder>() {

    //Clase ViewHolder, que necesitará nuestra clase Adaptador
    //Esta clase hereda de RecyclerView.ViewHolder(vista de cada item (View))
    class JugadoresViewHolder(val item: View): RecyclerView.ViewHolder(item) {
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

    //Se ejecutará al crearse el ViewHolder, que acepta un ViewGroup (Vista padre) y un tipo de vista.
    //Esta clase hereda de nuestro ViewHolder.
    //Construye (Inflate) un item (un View), con LayoutInflater, desde la vista padre, y construye
    // la vista del hijo (listitem_jugador) como un LinearLayout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listitem_jugador,parent,false) as LinearLayout
        return JugadoresViewHolder(item)
    }

    //Llama al método bindJugador, pasandole el jugador en la posicion de la lista correspondiente.
    //Acepta como parametros, un ViewHolder (el nuestro) y una posicion.
    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int) {
        val jugador = datos[position]
        holder.bindJugador(jugador)

        //Tambien, a cada item (View), le asigna un listener a cada uno.
        holder.item.setOnClickListener {clickListener(jugador)}
    }

    //Obtiene la cantidad de items.
    override fun getItemCount(): Int = datos.size
}