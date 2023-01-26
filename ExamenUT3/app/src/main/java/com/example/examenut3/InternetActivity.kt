package com.example.examenut3

//noinspection SuspiciousImport

import android.R
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examenut3.databinding.ActivityInternetBinding
import java.util.regex.Pattern

/* USED: SHOW URL INTO A WEB-VIEW AND CHECK/ADD PERMISSIONS */
class InternetActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInternetBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListener()
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this@InternetActivity, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListener() {
        /* Firstly, checks if the user have internet connection,
        *  if they have it, checks the URL typed by themselves, and if
        *  everything it's correct, shows it into a WebView */
        viewBinding.searchBtn.setOnClickListener {
            if (checkNetwork(this)) {
                val url = viewBinding.urlEt.text.toString()

                if (url.isNotEmpty()) {
                    if (Pattern.matches("^(https|http)://([a-zA-Z0-9](\\.)?(/)?)+", url)) {
                        viewBinding.webView.webViewClient = WebViewClient()
                        viewBinding.webView.loadUrl(url)
                    } else {
                        showMessage("Incorrect URL format")
                    }
                } else {
                    showMessage("Please, insert an URL")
                }
            } else {
                showMessage("You're not connected")
            }
        }
    }

    /* Checks our network connectivity, depending on the VERSION,
    *  the obtaining method will be different */
    private fun checkNetwork(context: Context): Boolean {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val netInfo = connectionManager.activeNetworkInfo
            return (netInfo != null && netInfo.isConnected)
        } else {
            val activeNetwork = connectionManager.activeNetwork ?: return false
            val netCapabilities = connectionManager.getNetworkCapabilities(activeNetwork)
            return (netCapabilities!!.hasCapability(NET_CAPABILITY_INTERNET))
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}