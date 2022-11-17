package com.example.red_web_and_sound

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.red_web_and_sound.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    //TODO desde el apartado 16.-
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.checkConnButton.setOnClickListener {
            if (checkNet(this)) {
                val type = connType(this)
                showMessage("Conectado a Internet mediante ${type}")
            }else{
                showMessage("No estas conectado a Internet")
            }
        }

        viewBinding.navButton.setOnClickListener {
            val url = viewBinding.pageUrlEditT.text.toString()

            if (url.isNotEmpty()) {
                if (checkUrl(url)) {
                    val webIntent = Intent(this@MainActivity,WebViewActivity::class.java)
                    webIntent.putExtra("url", url)

                    startActivity(webIntent)
                    this.finish()
                }else{
                    showMessage("Formato de URL incorrecto")
                }
            }else{
                showMessage("Inserte un url")
            }
        }

        viewBinding.soundPlayButton.setOnClickListener {
            val soundPlayerIntent = Intent(this@MainActivity,SoundPlayerActivity::class.java)
            startActivity(soundPlayerIntent)
            this.finish()
        }
    }

    private fun checkUrl(url:String): Boolean {
        return Pattern.matches("^(https|http)://([a-zA-Z0-9](\\.)?(/)?)+",url)
    }

    private fun checkNet(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val redInfo = connectionManager.activeNetworkInfo
            return (redInfo != null && redInfo.isConnected)
        } else {
            val activeNetwork = connectionManager.activeNetwork ?: return false
            val netCapabilities = connectionManager.getNetworkCapabilities(activeNetwork)
            return (netCapabilities!!.hasCapability(NET_CAPABILITY_INTERNET))
        }
    }

    private fun connType(context: Context): String {
        val conectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val connection = conectionManager.activeNetworkInfo
            when(connection!!.type) {
                ConnectivityManager.TYPE_MOBILE -> {return "Datos Móviles"}
                ConnectivityManager.TYPE_WIFI -> {return "WIFI"}
                else -> {return ""}
            }
        }else{
            val activeNetwork = conectionManager.getNetworkCapabilities(conectionManager.activeNetwork)
            if (activeNetwork!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return  "Mobile data"
            else
                if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return "Wifi"
        }
        return "Internet OFF"
    }

    private fun showMessage(message:String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}