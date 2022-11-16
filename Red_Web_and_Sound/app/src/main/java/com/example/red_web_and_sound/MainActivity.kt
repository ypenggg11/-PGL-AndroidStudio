package com.example.red_web_and_sound

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.red_web_and_sound.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //TODO Desde Apartado 7
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.checkConnButton.setOnClickListener {
            if (checkNet(this)) {
                //val type = connType(this)
                //showMessage("Conectado a Internet mediante ${type}")
            }else{
                //showMessage("No estas conectado a Internet")
            }
        }
    }

    fun checkNet(context: Context): Boolean {
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
}