package com.example.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    //TODO Controlar campos vacíos, terminar la parte final,
    //TODO refactorizar y comentar todo + CheatSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val teamIdEditT = viewBinding.teamIdEditT
        val teamNameEditT = viewBinding.teamNameEditT
        val numTitlesEditT = viewBinding.numTitlesEditT

        viewBinding.insertButton.setOnClickListener {
            val team = AdminSQLiteOpenHelper(this,"Teams",null,1)
            val dataBase = team.writableDatabase

            val row = ContentValues()
            row.put("team_id",teamIdEditT.text.toString().toInt())
            row.put("team_name",teamNameEditT.text.toString())
            row.put("num_titles",numTitlesEditT.text.toString().toInt())

            dataBase.insert("Teams",null,row)
            dataBase.close()

            teamIdEditT.setText("")
            teamNameEditT.setText("")
            numTitlesEditT.setText("")
            showToast("Row saved!")
        }

        viewBinding.searchButton.setOnClickListener {
            val team = AdminSQLiteOpenHelper(this,"Teams",null,1)
            val dataBase = team.writableDatabase

            val row = dataBase.rawQuery("SELECT team_name,num_titles FROM Teams WHERE team_id=${teamIdEditT.text.toString().toInt()}",null)
            if (row.moveToFirst()){
                teamNameEditT.setText(row.getString(0))
                numTitlesEditT.setText(row.getInt(1).toString())
            }else{
                showToast("That team doesn't exists!")
            }

            dataBase.close()
        }

        viewBinding.alterButton.setOnClickListener {
            if(teamIdEditT.text.toString().isNotEmpty()){
                val team = AdminSQLiteOpenHelper(this,"Teams",null,1)
                val dataBase = team.writableDatabase

                val rowData = ContentValues()
                rowData.put("team_name",teamNameEditT.text.toString())
                rowData.put("num_titles",numTitlesEditT.text.toString().toInt())

                val rowUpdate = dataBase.update("Teams",rowData,"team_id=${teamIdEditT.text.toString().toInt()}",null)
                if (rowUpdate==1){
                    showToast("Row updated!")
                }else{
                    showToast("Row update failed!")
                }

                dataBase.close()
            }else{
                showToast("Please insert an id to modify.")
            }

            teamIdEditT.setText("")
            teamNameEditT.setText("")
            numTitlesEditT.setText("")
        }

        viewBinding.deleteButton.setOnClickListener {
            if(teamIdEditT.text.toString().isNotEmpty()){
                val team = AdminSQLiteOpenHelper(this,"Teams",null,1)
                val dataBase = team.writableDatabase

                val rowUpdate = dataBase.delete("Teams","team_id=${teamIdEditT.text.toString().toInt()}",null)
                if (rowUpdate==1){
                    showToast("Row deleted!")
                }else{
                    showToast("Row delete failed!")
                }

                dataBase.close()
            }else{
                showToast("Please insert an id to modify.")
            }

            teamIdEditT.setText("")
            teamNameEditT.setText("")
            numTitlesEditT.setText("")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}