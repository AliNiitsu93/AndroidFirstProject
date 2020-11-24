package com.example.practice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)



        btnReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //start the next activity
            startActivity(intent)
        }
        btnScan.setOnClickListener {
            val intent = Intent(this, BarcodeScan::class.java)
            startActivity(intent)
        }
    }
}