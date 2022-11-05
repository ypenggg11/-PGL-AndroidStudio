package com.example.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

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

            showToast("Row saved!")
        }

        viewBinding.searchButton
        viewBinding.alterButton
        viewBinding.deleteButton

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}