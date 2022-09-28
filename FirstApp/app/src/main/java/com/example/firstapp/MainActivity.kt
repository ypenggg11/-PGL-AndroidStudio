package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Crea el listener y le asigna su acción para el botón 'buttonReverse'
        reverseButtonListener()

        //Crea el listener y le asigna su acción para el botón 'buttonSubString'
        extractButtonListener()
    }

    private fun extractButtonListener() {
        //Carga el componente correspondiente al id en una constante
        //findViewByID<'Clase del componente'>(R.id.'id del componente')
        val extractButton = findViewById<Button>(R.id.buttonSubString)
        val userInputEditText = findViewById<EditText>(R.id.editTextUserInput)
        val initialPosition = findViewById<EditText>(R.id.editTextInitialPos)
        val finalPosition = findViewById<EditText>(R.id.editTextLastPos)

        val extractedText = findViewById<TextView>(R.id.textViewSubString)

        //Listener que se activará al darle click
        extractButton.setOnClickListener {
            //.text será equivalente a .getText() en java si se usa para almacenarse (var/val)
            val userInput = userInputEditText.text.toString()
            var initialPos = 0
            var finalPos = 0

            if (!(initialPosition.text.toString() == "" || finalPosition.text.toString() == "")) {

                //Le restamos -1 para que no tenga q contar desde la posición 0
                initialPos = Integer.parseInt(initialPosition.text.toString()) - 1
                finalPos = Integer.parseInt(finalPosition.text.toString())
            }

            if (initialPos < finalPos && finalPos <= userInput.length && initialPos >= 0) {
                //.text será equivalente a un .setText() en java si se usa igualando a algún valor
                extractedText.text = userInput.substring(initialPos, finalPos)
            }

        }
    }

    private fun reverseButtonListener() {
        //Carga el componente correspondiente al id en una constante
        //findViewByID<'Clase del componente'>(R.id.'id del componente')
        val reverseButton = findViewById<Button>(R.id.buttonReverse)
        val userInputEditText = findViewById<EditText>(R.id.editTextUserInput)
        val reversedText = findViewById<TextView>(R.id.textViewReversed)

        //Listener que se activará al darle click
        reverseButton.setOnClickListener {
            //.text será equivalente a .getText() en java si se usa para almacenarse (var/val)
            val userInput = userInputEditText.text.toString()

            //.text será equivalente a un .setText() en java si se usa igualando a algún valor
            //el método .reversed() devolverá una String inversa
            reversedText.text = userInput.reversed()
        }
    }
}
