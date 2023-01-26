package com.example.examen_ut3

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.examen_ut3.databinding.ActivityHilosBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HilosActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityHilosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHilosBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListeners()
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this@HilosActivity, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        viewBinding.calcularBtn.setOnClickListener {
            if (viewBinding.inicioEt.text.isNotBlank() && viewBinding.finEt.text.isNotBlank()) {
                val inicio = viewBinding.inicioEt.text.toString().toInt()
                val final = viewBinding.finEt.text.toString().toInt()
                calculaPrimos(inicio, final)
            } else {
                showMessage("No input detected")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun calculaPrimos(inicio: Int, final: Int) {
        /* Execute the next code as IO Dispatcher, better performance because uses another thread */
        GlobalScope.launch(Dispatchers.IO) {

            var contador = inicio + 1

            while (contador < final) {
                var esPrimo = true

                for (i in 2 until contador) {
                    if (contador % i == 0) {
                        esPrimo = false
                        break
                    }
                }

                if (esPrimo) {
                    launch(Dispatchers.Main) {
                        viewBinding.primosTv.text = contador.toString()
                    }
                }

                Thread.sleep(1000)
                contador++
            }
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}