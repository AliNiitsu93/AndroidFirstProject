package com.example.practice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        btnStartShopping.setOnClickListener {
            val intent = Intent(this, ShoppingCart::class.java)
            //start the next activity
            startActivity(intent)
        }

        btnSaveProduct.setOnClickListener {
            val intent = Intent(this, AddProduct::class.java)
            startActivity(intent)
        }

    }
}


//DATABASE IS NOT BEING CREATED Currently.