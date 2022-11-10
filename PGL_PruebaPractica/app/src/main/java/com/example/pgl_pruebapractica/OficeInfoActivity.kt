package com.example.pgl_pruebapractica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class OficeInfoActivity : AppCompatActivity() {

    private lateinit var localidadTextV:TextView
    private lateinit var codOficinaTextV:TextView
    private lateinit var direccionOficinaTextV:TextView
    private lateinit var telefonoOficinaTextV:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ofice_info)

        initViews()

        initTextViewValues()

        initListeners()
    }

    private fun initTextViewValues() {
        val bundle = intent.extras
        val localidad: String? = bundle?.getString("selectedLocalidad")

        localidadTextV.text = localidad

        val oficina = OficinaSQLiteOpenHelper(this, "Oficina", null, 1)
        val dataBase = oficina.writableDatabase

        val row = dataBase.rawQuery(
            "SELECT cod_oficina,direccion,telefono FROM Oficina WHERE localidad LIKE ('${localidadTextV.text.toString()}')",
            null
        )

        if (row.moveToFirst()) {
            codOficinaTextV.text = row.getInt(0).toString()
            direccionOficinaTextV.text = row.getString(1)
            telefonoOficinaTextV.text = row.getString(2)
        }

        dataBase.close()
    }

    private fun initListeners() {
        findViewById<Button>(R.id.volverButton).setOnClickListener {
            val databaseIntent = Intent(this@OficeInfoActivity, DataBaseActivity::class.java)

            startActivity(databaseIntent)

            finish()
        }
    }

    private fun initViews() {
        localidadTextV = findViewById(R.id.localidadOficinaTextV)
        codOficinaTextV = findViewById(R.id.codOficinaTextV)
        direccionOficinaTextV = findViewById(R.id.direccionOficinaTextV)
        telefonoOficinaTextV = findViewById(R.id.telefonoOficinaTextV)
    }
}