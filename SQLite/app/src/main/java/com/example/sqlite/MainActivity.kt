package com.example.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.R
import androidx.appcompat.app.AlertDialog
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private lateinit var teamIdEditT: EditText
    private lateinit var teamNameEditT: EditText
    private lateinit var numTitlesEditT: EditText

    //TODO Refactorizar y comentar todo + CheatSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initWidgets()
        initListeners()
    }

    //Declaramos todos los listener de nuestros widgets.
    private fun initListeners() {

        //Inserta en nuestra bbdd, un nuevo registro en la tabla 'Teams'.
        viewBinding.insertButton.setOnClickListener {
            if (
                teamIdEditT.text.toString().isNotEmpty() &&
                teamNameEditT.text.toString().isNotEmpty() &&
                numTitlesEditT.text.toString().isNotEmpty()
            ) {
                //Instancia de nuestra base de datos.
                val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)

                //Hace que nuestra bbdd sea de lectura y escritura.
                val dataBase = team.writableDatabase

                //Registro con los valores que insertaremos
                val row = ContentValues()
                row.put("team_id", teamIdEditT.text.toString().toInt())
                row.put("team_name", teamNameEditT.text.toString())
                row.put("num_titles", numTitlesEditT.text.toString().toInt())

                //Insertamos el nuestro objeto ContentValues (registro) en la tabla 'Teams'
                dataBase.insert("Teams", null, row)
                dataBase.close()

                clearInput()
                showToast("Row saved!")
            } else {
                showToast("Please fill all empty fields.")
            }
        }

        //Busca en nuestra bbdd, un registro de la tabla 'Teams' en base a su id.
        viewBinding.searchButton.setOnClickListener {
            if (teamIdEditT.text.toString().isNotEmpty()) {
                //Instancia de nuestra base de datos.
                val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)

                //Hace que nuestra bbdd sea de lectura y escritura.
                val dataBase = team.writableDatabase

                //Realizamos una consulta y recuperamos el resultado.
                val row = dataBase.rawQuery(
                    "SELECT team_name,num_titles FROM Teams WHERE team_id=${
                        teamIdEditT.text.toString().toInt()
                    }", null
                )

                //Verifica si se ha recuperado algún resultado.
                if (row.moveToFirst()) {
                    teamNameEditT.setText(row.getString(0))
                    numTitlesEditT.setText(row.getInt(1).toString())

                } else {
                    showToast("That team doesn't exists!")
                }

                dataBase.close()
            } else {
                showToast("Please insert an id to search.")
            }
        }

        //Modifica en nuestra bbdd, un registro de la tabla 'Teams' en base a su id.
        viewBinding.alterButton.setOnClickListener {
            if (teamIdEditT.text.toString().isNotEmpty()) {
                //Instancia de nuestra base de datos.
                val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)

                //Hace que nuestra bbdd sea de lectura y escritura.
                val dataBase = team.writableDatabase

                //Registro con los valores que modificaremos.
                val rowData = ContentValues()
                rowData.put("team_name", teamNameEditT.text.toString())
                rowData.put("num_titles", numTitlesEditT.text.toString().toInt())

                //Actualizamos dicho registro con los nuevos datos según su id.
                val rowUpdate = dataBase.update(
                    "Teams",
                    rowData,
                    "team_id=${teamIdEditT.text.toString().toInt()}",
                    null
                )

                //Verifica si se ha actualizado correctamente.
                if (rowUpdate == 1) {
                    showToast("Row updated!")
                } else {
                    showToast("Row update failed!")
                }

                dataBase.close()
                clearInput()
            } else {
                showToast("Please insert an id to modify.")
            }
        }

        //Elimina de nuestra bbdd, un registro de la tabla 'Teams' en base a su id.
        viewBinding.deleteButton.setOnClickListener {
            if (teamIdEditT.text.toString().isNotEmpty()) {
                //Instancia de nuestra base de datos.
                val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)

                //Hace que nuestra bbdd sea de lectura y escritura.
                val dataBase = team.writableDatabase

                //Elimina de la bbdd un registro según su id.
                val rowDelete =
                    dataBase.delete("Teams", "team_id=${teamIdEditT.text.toString().toInt()}", null)

                //Verifica si se ha eliminado correctamente.
                if (rowDelete == 1) {
                    showToast("Row deleted!")
                } else {
                    showToast("Row delete failed!")
                }

                dataBase.close()
                clearInput()
            } else {
                showToast("Please insert an id to delete.")
            }
        }

        /*
            Busca en nuestra bbdd, todos los registros en la tabla 'Teams'
            y añade los nombres a un Spinner con un Adapter.
         */
        viewBinding.allButton.setOnClickListener {
            //Instancia de nuestra base de datos.
            val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)

            //Hace que nuestra bbdd sea de lectura y escritura.
            val dataBase = team.writableDatabase

            //Creamos un ArrayList
            val teamsList = ArrayList<String>()

            //Ejecutamos y recuperamos los resultados de una consulta
            val rows = dataBase.rawQuery("SELECT team_name FROM Teams", null)

            //Mientras haya registros, añadira a la lista el nombre del equipo.
            while (rows.moveToNext()) {
                teamsList.add(rows.getString(0))
            }

            //Creamos un adaptador para el Spinner
            val arrayAdapter =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, teamsList)

            //Le pasamos el adaptador creado al Spinner
            viewBinding.teamsSpinner.adapter = arrayAdapter
            dataBase.close()
        }

        //Muestra una alerta con el item seleccionado del Spinner.
        viewBinding.teamsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val alertDialog = AlertDialog.Builder(this@MainActivity)
                    alertDialog.setMessage("You've selected ${p0!!.getItemAtPosition(p2)}")
                    alertDialog.show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

    //Limpia el texto de los EditText
    private fun clearInput() {
        teamIdEditT.setText("")
        teamNameEditT.setText("")
        numTitlesEditT.setText("")
    }

    //Inicializa los widgets que necesitamos de la activity
    private fun initWidgets() {
        teamIdEditT = viewBinding.teamIdEditT
        teamNameEditT = viewBinding.teamNameEditT
        numTitlesEditT = viewBinding.numTitlesEditT
    }

    //Muestra un mensaje en Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}