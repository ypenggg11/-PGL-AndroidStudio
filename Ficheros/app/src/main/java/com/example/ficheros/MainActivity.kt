package com.example.ficheros

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.ficheros.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initListeners()
    }

    /**
     * Inicia los listeners de nuestros componentes.
     */
    private fun initListeners() {
        val fechaEditT = viewBinding.fechaEditT
        val datosEditT = viewBinding.datosEditT

        viewBinding.grabarButton.setOnClickListener {
            saveFile(fechaEditT, datosEditT)
        }

        viewBinding.recuperarButton.setOnClickListener {
            recoverFile(fechaEditT, datosEditT)
        }
    }

    /**
     * Recupera los datos de un fichero según su nombre.
     * @param fileName Campo del nombre del fichero.
     * @param dataField Campo de los datos del fichero.
     */
    private fun recoverFile(
        fileName: EditText,
        dataField: EditText,
    ) {
        //Reemplazamos '/' por '-', para que sea un nombre válido.
        val nombreFichero = fileName.text.toString().replace('/', '-')

        //En caso de que contenga en nuestra lista de ficheros, un nombre en concreto...
        if (fileList().contains(nombreFichero)) {

            //Lectura del fichero con BufferedReader(InputStreamReader())
            try {
                val fileBuffer = BufferedReader(InputStreamReader(openFileInput(nombreFichero)))
                var line = fileBuffer.readLine()

                val fileContent = StringBuilder()

                while (line != null) {
                    fileContent.append(line + "\n")
                    line = fileBuffer.readLine()
                }

                fileBuffer.close()

                dataField.setText(fileContent)
            } catch (_: IOException) {
            }
        } else {
            showToast("No existe un fichero asociada a dicha fecha")
        }
    }

    /**
     * Guarda un fichero con un nombre dado.
     */
    private fun saveFile(
        fileName: EditText,
        dataField: EditText,
    ) {
        //En caso de que los campos de texto no estén vacíos...
        if (fileName.text.trim().isNotEmpty() && dataField.text.trim().isNotEmpty()) {

            //En caso de que no contenga el caracter '.' (ya que queremos que introduzca fechas)
            if (!fileName.text.toString().contains('.')) {

                //Reemplazamos '/' por '-', para que sea un nombre válido.
                val nombreFichero = fileName.text.toString().replace('/', '-')

                //Escritura del fichero con OutputStreamWriter()
                try {
                    val file =
                    OutputStreamWriter(openFileOutput(nombreFichero, Activity.MODE_PRIVATE))
                    file.write(dataField.text.toString())
                    file.flush()
                    file.close()
                } catch (_: IOException) {
                }

                showToast("Fichero guardado")

                fileName.setText("")
                dataField.setText("")
            }
        }
    }

    /**
     * Muestra un Toast con un mensaje dado.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}