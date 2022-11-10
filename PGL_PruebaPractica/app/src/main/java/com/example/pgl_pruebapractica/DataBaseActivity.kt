package com.example.pgl_pruebapractica

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class DataBaseActivity : AppCompatActivity() {

    private lateinit var codEditText:EditText
    private lateinit var direccionEditT:EditText
    private lateinit var localidadEditT:EditText
    private lateinit var telefonoEditT:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base)

        initViews()

        initListeners()
    }

    private fun initListeners() {
        findViewById<Button>(R.id.insertButton).setOnClickListener {
            if (codEditText.text.isNotEmpty() && direccionEditT.text.isNotEmpty() && localidadEditT.text.isNotEmpty() && telefonoEditT.text.isNotEmpty()) {
                val oficina = OficinaSQLiteOpenHelper(this, "Oficina", null, 1)
                val dataBase = oficina.writableDatabase

                val registro = ContentValues()
                registro.put("cod_oficina", codEditText.text.toString().toInt())
                registro.put("direccion", direccionEditT.text.toString())
                registro.put("localidad", localidadEditT.text.toString())
                registro.put("telefono", telefonoEditT.text.toString())

                dataBase.insert("Oficina", null, registro)

                showToast("Registro insertado!")

                clearEditT()

                dataBase.close()
            } else {
                showToast("Rellene todos los campos!")
            }
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            if (codEditText.text.isNotEmpty()) {
                val oficina = OficinaSQLiteOpenHelper(this, "Oficina", null, 1)
                val dataBase = oficina.writableDatabase

                val rowDelete = dataBase.delete(
                    "Oficina",
                    "cod_oficina=${codEditText.text.toString().toInt()}",
                    null
                )

                if (rowDelete == 1) {
                    showToast("Registro eliminado!")

                } else {
                    showToast("Codigo invalido!")
                }

                clearEditT()

                dataBase.close()
            } else {
                showToast("Inserte el codigo a eliminar!")
            }
        }

        findViewById<Button>(R.id.showLocalidadesButton).setOnClickListener {
            val oficina = OficinaSQLiteOpenHelper(this, "Oficina", null, 1)
            val dataBase = oficina.writableDatabase

            val localidades = ArrayList<String>()
            localidades.add("")

            val rows = dataBase.rawQuery("SELECT localidad FROM Oficina", null)

            while (rows.moveToNext()) {
                localidades.add(rows.getString(0))
            }

            val arrayAdapter =
                ArrayAdapter(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    localidades
                )

            findViewById<Spinner>(R.id.localidadesSpinner).adapter = arrayAdapter

            dataBase.close()
        }

        findViewById<Spinner>(R.id.localidadesSpinner).onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedLocalidad = p0!!.getItemAtPosition(p2).toString()

                    if (selectedLocalidad != "") {
                        val oficeInfoIntent =
                            Intent(this@DataBaseActivity, OficeInfoActivity::class.java)
                        oficeInfoIntent.putExtra("selectedLocalidad", selectedLocalidad)

                        startActivity(oficeInfoIntent)

                        finish()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        findViewById<Button>(R.id.backButton).setOnClickListener {
            val mainIntent = Intent(this@DataBaseActivity, MainActivity::class.java)

            startActivity(mainIntent)

            finish()
        }
    }

    private fun clearEditT() {
        codEditText.setText("")
        direccionEditT.setText("")
        localidadEditT.setText("")
        telefonoEditT.setText("")
    }

    private fun initViews() {
        codEditText = findViewById(R.id.codOficinaEditT)
        direccionEditT = findViewById(R.id.direccionEditT)
        localidadEditT = findViewById(R.id.localidadEditT)
        telefonoEditT = findViewById(R.id.telefonoEditT)
    }

    private fun showToast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }
}
