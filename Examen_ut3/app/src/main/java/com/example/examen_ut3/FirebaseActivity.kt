package com.example.examen_ut3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.examen_ut3.databinding.ActivityFirebaseBinding
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFirebaseBinding

    /* Database declarations */
    private val db = FirebaseFirestore.getInstance()
    private val oficinas = db.collection("Oficinas")

    /* Views declarations */
    private lateinit var codOfic: EditText
    private lateinit var nombre: EditText
    private lateinit var direccion: EditText
    private lateinit var localidad: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initListeners()

        mostratTodas()
    }

    private fun mostratTodas() {
        val lista = ArrayList<String>()

        oficinas.get().addOnSuccessListener {
            for (docu in it) {
                lista.add(docu.id)
            }
            val ArrayOfic = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lista)
            viewBinding.spinner.adapter = ArrayOfic
        }
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@FirebaseActivity, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        viewBinding.insertBtn.setOnClickListener {
            insertToDb()
        }

        viewBinding.searchDbBtn.setOnClickListener {
            searchFromDb()
        }

        viewBinding.updateBtn.setOnClickListener {
            updateFromDb()
        }

        viewBinding.deleteBtn.setOnClickListener {
            deleteFromDb()
        }
    }

    private fun insertToDb() {
        if (
            codOfic.text.isNotBlank() &&
            nombre.text.isNotBlank() &&
            direccion.text.isNotBlank() &&
            localidad.text.isNotBlank()
        ) {
            /* Create an instance of our data class */
            val ofic = Oficinas(
                codOfic.text.toString(),
                nombre.text.toString(),
                direccion.text.toString(),
                localidad.text.toString()
            )

            /* Adds the created data class to our Firestore Database -> (document(customId)) */
            oficinas.document(ofic.cod_oficina).set(ofic)

            showMessage("Inserted!")
            clearEditText()
        } else {
            showMessage("Fill all fields!")
        }
    }

    private fun searchFromDb() {
        if (codOfic.text.isNotBlank()) {
            /* Gets a document by id, and if exists print it in our edit text */
            oficinas.document(codOfic.text.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    /* it.get( DATABASE FIELD NAME ) */
                    codOfic.setText(it.get("cod_oficina").toString())
                    nombre.setText(it.get("nombre").toString())
                    direccion.setText(it.get("direccion").toString())
                    localidad.setText(it.get("localidad").toString())

                    showMessage("Office found!")
                } else {
                    showMessage("Office Id not found!")
                }
            }
        } else {
            showMessage("Office Id not found!")
        }
    }

    private fun updateFromDb() {
        if (
            codOfic.text.isNotBlank() &&
            nombre.text.isNotBlank() &&
            direccion.text.isNotBlank() &&
            localidad.text.isNotBlank()
        ) {
            /* Gets a document by id */
            oficinas.document(
                codOfic.text.toString()
            ).set(
                /* Updates the document with .set(hashMapOf( DB FIELD NAME to NEW VALUE )) */
                hashMapOf(
                    "cod_oficina" to codOfic.text.toString(), "nombre" to nombre.text.toString(),
                    "direccion" to direccion.text.toString(), "localidad" to localidad.text.toString()
                )
            )

            showMessage("Updated!")
            clearEditText()
        } else {
            showMessage("Fill all fields!")
        }
    }

    private fun deleteFromDb() {
        if (codOfic.text.isNotBlank()) {
            /* Gets a document by id */
            oficinas.document(codOfic.text.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    /* Gets it reference and delete that document */
                    it.reference.delete()

                    showMessage("Deleted!")
                    clearEditText()
                } else {
                    showMessage("Office Id not found!")
                }
            }
        } else {
            showMessage("Office Id not found!")
        }
    }

    private fun initViews() {
        codOfic = viewBinding.codOficEt
        nombre = viewBinding.nombreEt
        direccion = viewBinding.direccionEt
        localidad = viewBinding.localidadEt
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun clearEditText() {
        codOfic.setText("")
        nombre.setText("")
        direccion.setText("")
        localidad.setText("")
    }
}