package com.example.autenticacion

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    /* Components declarations */
    private lateinit var authentication: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var userAmount: TextView
    private lateinit var registeredTv: TextView

    /* Data declarations */
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initComponents()
        initSharedPreferences()
        initListeners()
    }

    private fun initSharedPreferences() {
        val preferences = getSharedPreferences("registeredUsers", Context.MODE_PRIVATE)
        /*val editor = preferences.edit()
        editor.putInt("userAmount", 1493)
        editor.apply()*/

        val data = preferences.getInt("userAmount",1493)
        userAmount.text = (data).toString()
    }

    private fun initComponents() {
        authentication = FirebaseAuth.getInstance()
        emailEt = viewBinding.emailEt
        passwordEt = viewBinding.passwdEt

        userAmount = viewBinding.userAmountTv
        registeredTv = viewBinding.registeredTv
    }

    private fun initListeners() {

        /*


        /* LOGIN */
        viewBinding.loginBtn.setOnClickListener {
            login()
        }

         */


        /* REGISTER */
        viewBinding.registerBtn.setOnClickListener {
            register()
        }
    }

    /*
    private fun login() {
        if (emailEt.text.isNotBlank() && passwordEt.text.isNotBlank()) {

            email = emailEt.text.toString()
            password = passwordEt.text.toString()

            authentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        showMessage("Logged in")
                    } else {
                        showMessage("Wrong credentials")
                    }
                }
        } else {
            showMessage("Please, fill all fields")
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun register() {
        if (emailEt.text.isNotBlank() && passwordEt.text.isNotBlank()) {

            email = emailEt.text.toString()
            password = passwordEt.text.toString()

            authentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        sendEmailVerification()
                    } else {
                        increaseUserAmount()

                        emailEt.isVisible = false
                        passwordEt.isVisible = false
                        viewBinding.registerBtn.isVisible = false
                        registeredTv.isVisible = true
                    }
                }
        } else {
            showMessage("Please, fill all fields")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun increaseUserAmount() {
        val preferences = getSharedPreferences("registeredUsers", Context.MODE_PRIVATE)
        val data = preferences.getInt("userAmount",1493)
        val editor = preferences.edit()
        editor.putInt("userAmount", (data+1))
        editor.apply()

        userAmount.text = (data+1).toString()
    }

    private fun sendEmailVerification() {
        val user = authentication.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showMessage("Please verify your email and try again")
                    passwordEt.setText("")
                }
            }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}