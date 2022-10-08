package com.example.zonaestadio_act3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setZonasRgListener()

        setAbonadoCbListener()
    }

    private fun setAbonadoCbListener() {
        val abonadoCheckB = findViewById<CheckBox>(R.id.abonadoCheckBox)

        abonadoCheckB.setOnCheckedChangeListener { compoundButton, selected ->
            //Crea una alerta.
            val alert = AlertDialog.Builder(this).create()

            if (selected) {

                //Añade el mensaje a la alerta.
                alert.setMessage(getText(R.string.abanado_checkBox_True))
            } else {

                //getText() -> devuelve el texto dentro de la etiqueda
                //que le indiquemos del archivo 'strings.xml'
                //(Funciona con etiquetas html)
                alert.setMessage(getText(R.string.abonado_checkBox_False))
            }

            //Muestra la alerta.
            alert.show()
        }
    }

    private fun setZonasRgListener() {
        val zonasRadioG = findViewById<RadioGroup>(R.id.zonasRadioGroup)
        val zonaElegidaTextV = findViewById<TextView>(R.id.zonaElegidaTextView)

        zonasRadioG.setOnCheckedChangeListener { radioGroup, selected ->

            when (selected) {
                //Cuando el botón seleccionado sea el que corresponde con la id...
                R.id.zona1RadioButton -> {

                    //getString() = getText(), pero no funciona con etiquetas html.
                    zonaElegidaTextV.text =
                        "Has seleccionado ${getString(R.string.zona1_radioButton)}"
                }
                R.id.zona2RadioButton -> {
                    zonaElegidaTextV.text =
                        "Has seleccionado ${getString(R.string.zona2_radioButton)}"
                }
                R.id.zona3RadioButton -> {
                    zonaElegidaTextV.text =
                        "Has seleccionado ${getString(R.string.zona3_radioButton)}"
                }
                R.id.zona4RadioButton -> {
                    zonaElegidaTextV.text =
                        "Has seleccionado ${getString(R.string.zona4_radioButton)}"
                }
            }
        }
    }
}