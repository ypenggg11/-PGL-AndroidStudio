package com.example.examenut3

//noinspection SuspiciousImport
import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.examenut3.databinding.ActivityThreadBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/* USED: Asynchronous task using COROUTINES (BEST OPTION FOR > API 30 APPS) */
class ThreadActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityThreadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityThreadBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListeners()

    }

    private fun initListeners() {
        viewBinding.timerBtn.setOnClickListener {
            if (viewBinding.timerEt.text.isNotBlank()) {

                val seconds = viewBinding.timerEt.text.toString().toInt()
                countDownCoroutine(seconds)

            } else {
                showMessage("No input detected")
            }
        }
    }

    /* Coroutine for countdown */
    @OptIn(DelicateCoroutinesApi::class)
    private fun countDownCoroutine(s: Int) {
        /* Execute the next code as IO Dispatcher, better performance because uses another thread */
        GlobalScope.launch(Dispatchers.IO) {
            var seconds = s

            while (seconds >= 0) {

                /* Use Dispatcher Main for code that interact with our view components */
                launch(Dispatchers.Main) {
                    viewBinding.timerTv.text = seconds.toString()
                    viewBinding.timerBtn.isClickable = false
                }

                try {
                    Thread.sleep(1000)
                    seconds--
                } catch (_: InterruptedException) {
                }
            }

            /* Use Dispatcher Main for code that interact with our view components */
            launch(Dispatchers.Main) {
                viewBinding.timerBtn.isClickable = true
                showMessage("Timer finished!")
            }
        }
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this@ThreadActivity, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}