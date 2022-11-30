package com.example.hilos

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import com.example.hilos.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //TODO Comentar

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.progressBar = viewBinding.progressBar

        viewBinding.playMusicButton.setOnClickListener {
            if (viewBinding.playMusicButton.isChecked) {
                startService(Intent(this@MainActivity,PlayBgMusic::class.java))
            }else{
                stopService(Intent(this@MainActivity,PlayBgMusic::class.java))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.sinHilosItem -> {
                sinHilos()
            }

            R.id.threadItem -> {
                conThread()
            }

            R.id.asyncTaskItem -> {
                asyncTask()
            }

            R.id.corrutinasItem -> {
                corrutinas()
            }
        }
        return true
    }

    private fun sinHilos() {

        progressBar.max = 100
        progressBar.progress = 0

        for (i in 1 .. 10) {
            wait(1000)
            progressBar.incrementProgressBy(10)
        }

        showMessage("Tarea sin hilos finalizada")
    }

    /**
     * Cada vez que se vaya a realizar una tarea con una vista, ponerlo dentro de runOnUiThread
     */
    private fun conThread() {
        Thread{
            runOnUiThread{
                progressBar.max = 100
                progressBar.progress = 0
            }

            for (i in 1 .. 10) {
                runOnUiThread {
                    progressBar.incrementProgressBy(10)
                }
                wait(1000)
            }

            runOnUiThread {
                showMessage("Tarea con THREAD finalizada")
            }
        }.start()
    }

    private fun asyncTask() {
        val task = ProgressBarTask()
        task.execute()
    }

    inner class ProgressBarTask: AsyncTask<Void,Int,Void>() {

        override fun onPreExecute() {
            progressBar.max = 100
            progressBar.progress = 0
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 1 .. 10) {
                publishProgress(10)
                wait(1000)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            progressBar.incrementProgressBy(values[0]!!)
        }

        override fun onPostExecute(result: Void?) {
            showMessage("Tarea con ASYNCTASK finalizada")
        }

        override fun onCancelled() {
            showMessage("Tarea cancelada")
        }

    }

    private fun corrutinas() {
        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                progressBar.max = 100
                progressBar.progress = 0
            }

            for (i in 1 .. 10) {
                launch(Dispatchers.Main) {
                    progressBar.incrementProgressBy(10)
                }
                wait(1000)
            }

            launch(Dispatchers.Main) {
                showMessage("Tarea con CORRUTINAS finalizada")
            }
        }
    }

    private fun wait(miliSeconds: Int) {
        try{
            Thread.sleep(miliSeconds.toLong())
        }catch (_:InterruptedException) {}
    }

    private fun showMessage(message:String) {
        Toast.makeText(this@MainActivity,message,Toast.LENGTH_LONG).show()
    }
}