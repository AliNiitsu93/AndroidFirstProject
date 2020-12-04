package com.example.practice4

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class AddProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM PRODUCT",null)

        btnScanner.setOnClickListener {
            val intent = Intent(this, BarcodeScan::class.java)
            startActivity(intent)
        }

        btnSaveProd.setOnClickListener {
            var cv = ContentValues()
            //db?.execSQL("CREATE TABLE PRODUCT(P_BARCODE TEXT PRIMARY KEY , DESCRIPTION TEXT)")
            cv.put("P_BARCODE",txtPbarcode.text.toString())
            cv.put("DESCRIPTION",txtPdescription.text.toString())


            db.insert("PRODUCT", null, cv)

            txtPbarcode.setText("")
            txtPdescription.setText("")
        }
        btnBack.setOnClickListener {
            val intent = Intent(this, BarcodeScan::class.java)
            startActivity(intent)
        }

    }
}