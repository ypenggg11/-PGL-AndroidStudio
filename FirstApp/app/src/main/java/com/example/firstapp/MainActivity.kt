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

        reverseButtonListener()

        extractButtonListener()
    }

    private fun extractButtonListener() {
        val extractButton = findViewById<Button>(R.id.buttonSubString)
        val userInputEditText = findViewById<EditText>(R.id.editTextUserInput)
        val initialPosition = findViewById<EditText>(R.id.editTextInitialPos)
        val finalPosition = findViewById<EditText>(R.id.editTextLastPos)

        val extractedText = findViewById<TextView>(R.id.textViewSubString)

        extractButton.setOnClickListener {
            val userInput = userInputEditText.text.toString()
            var initialPos = 0
            var finalPos = 0

            if (!(initialPosition.text.toString() == "" || finalPosition.text.toString() == "")) {

                //Le restamos -1 para que no tenga q contar desde la posición 0
                initialPos = Integer.parseInt(initialPosition.text.toString()) - 1
                finalPos = Integer.parseInt(finalPosition.text.toString())
            }

            if (initialPos < finalPos && finalPos <= userInput.length && initialPos >= 0) {
                extractedText.text = userInput.substring(initialPos, finalPos)
            }

        }
    }

    private fun reverseButtonListener() {
        val reverseButton = findViewById<Button>(R.id.buttonReverse)
        val userInputEditText = findViewById<EditText>(R.id.editTextUserInput)
        val reversedText = findViewById<TextView>(R.id.textViewReversed)

        reverseButton.setOnClickListener {
            val userInput = userInputEditText.text.toString()
            reversedText.text = userInput.reversed()
        }
    }
}
