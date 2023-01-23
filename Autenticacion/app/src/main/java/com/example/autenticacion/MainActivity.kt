package com.example.autenticacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    /* Components declarations */
    private lateinit var authentication: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    /* Data declarations */
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        authentication = FirebaseAuth.getInstance()
        emailEt = viewBinding.emailEt
        passwordEt = viewBinding.passwdEt
    }

    private fun initListeners() {
        /* LOGIN */
        viewBinding.loginBtn.setOnClickListener {
            login()
        }

        /* REGISTER */
        viewBinding.registerBtn.setOnClickListener {
            register()
        }
    }

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
    }

    private fun register() {
        if (emailEt.text.isNotBlank() && passwordEt.text.isNotBlank()) {

            email = emailEt.text.toString()
            password = passwordEt.text.toString()

            authentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        showMessage("User created")
                    } else {
                        showMessage("Creation failed")
                    }
                }
        } else {
            showMessage("Please, fill all fields")
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}