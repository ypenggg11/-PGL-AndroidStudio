package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val fragSpinner = viewBinding.fragSpinner

        fragSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val seleccion = "${p0!!.getItemAtPosition(p2)}"

                val frag1 = FragmentA()
                val texto = Bundle()
                texto.putString("texto",seleccion)

                frag1.arguments = texto
                showFragment(frag1)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val frag1Button = viewBinding.fragment1Button
        val frag2Button = viewBinding.fragment2Button

        frag1Button.setOnClickListener {
            val frag1 = FragmentA()
            showFragment(frag1)
        }

        frag2Button.setOnClickListener {
            val frag2 = FragmentB()
            showFragment(frag2)
        }
    }

    private fun showFragment(frag: Fragment) {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragmentsFrameL, frag)
        fragment.commit()
    }
}