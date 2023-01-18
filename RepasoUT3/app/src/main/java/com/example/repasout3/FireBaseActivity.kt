package com.example.repasout3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.repasout3.databinding.ActivityFireBaseBinding
import com.example.repasout3.model.Products
import com.google.firebase.firestore.FirebaseFirestore

/* USED: Cloud Firestore -> Need to manually add an implementation (see build.gradle) */
class FireBaseActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFireBaseBinding

    /* Database declarations */
    private val db = FirebaseFirestore.getInstance()
    private val products = db.collection("Products")

    /* Views declarations */
    private lateinit var prodId_et: EditText
    private lateinit var prodName_et: EditText
    private lateinit var prodCountry_et: EditText
    private lateinit var prodPrice_et: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFireBaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initViews()
        initListeners()
    }

    private fun initListeners() {
        viewBinding.insertBtn.setOnClickListener {
            insertToDb()
        }

        viewBinding.searchDbBtn.setOnClickListener {
            searchFromDb()
        }

        viewBinding.updateBtn.setOnClickListener {
            updateFromDb()
        }

        viewBinding.deleteBtn.setOnClickListener {
            deleteFromDb()
        }
    }

    private fun insertToDb() {
        if (
            prodId_et.text.isNotBlank() &&
            prodName_et.text.isNotBlank() &&
            prodCountry_et.text.isNotBlank() &&
            prodPrice_et.text.isNotBlank()
        ) {
            /* Create an instance of our data class */
            val prod = Products(
                prodId_et.text.toString(),
                prodName_et.text.toString(),
                prodCountry_et.text.toString(),
                prodPrice_et.text.toString().toFloat()
            )

            /* Adds the created data class to our Firestore Database -> (document(customId)) */
            products.document(prod.prodId).set(prod)

            showMessage("Inserted!")
            clearEditText()
        } else {
            showMessage("Fill all fields!")
        }
    }

    private fun searchFromDb() {
        if (prodId_et.text.isNotBlank()) {
            /* Gets a document by id, and if exists print it in our edit text */
            products.document(prodId_et.text.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    /* it.get( DATABASE FIELD NAME ) */
                    prodId_et.setText(it.get("prodId").toString())
                    prodName_et.setText(it.get("prodName").toString())
                    prodCountry_et.setText(it.get("country").toString())
                    prodPrice_et.setText(it.get("price").toString())

                    showMessage("Product found!")
                } else {
                    showMessage("Product Id not found!")
                }
            }
        } else {
            showMessage("Product Id not found!")
        }
    }

    private fun updateFromDb() {
        if (
            prodId_et.text.isNotBlank() &&
            prodName_et.text.isNotBlank() &&
            prodCountry_et.text.isNotBlank() &&
            prodPrice_et.text.isNotBlank()
        ) {
            /* Gets a document by id */
            products.document(
                prodId_et.text.toString()
            ).set(
                /* Updates the document with .set(hashMapOf( DB FIELD NAME to NEW VALUE )) */
                hashMapOf(
                    "prodId" to prodId_et.text.toString(), "prodName" to prodName_et.text.toString(),
                    "country" to prodCountry_et.text.toString(), "price" to prodPrice_et.text.toString()
                )
            )

            showMessage("Updated!")
            clearEditText()
        } else {
            showMessage("Fill all fields!")
        }
    }

    private fun deleteFromDb() {
        if (prodId_et.text.isNotBlank()) {
            /* Gets a document by id */
            products.document(prodId_et.text.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    /* Gets it reference and delete that document */
                    it.reference.delete()

                    showMessage("Deleted!")
                    clearEditText()
                } else {
                    showMessage("Product Id not found!")
                }
            }
        } else {
            showMessage("Product Id not found!")
        }
    }

    private fun initViews() {
        prodId_et = viewBinding.prodIdEt
        prodName_et = viewBinding.prodNameEt
        prodCountry_et = viewBinding.countryEt
        prodPrice_et = viewBinding.priceEt
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun clearEditText() {
        prodId_et.setText("")
        prodName_et.setText("")
        prodCountry_et.setText("")
        prodPrice_et.setText("")
    }
}