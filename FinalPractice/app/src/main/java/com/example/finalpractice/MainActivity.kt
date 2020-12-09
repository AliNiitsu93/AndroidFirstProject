package com.example.finalpractice


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Just a title for the app
        title = "RememberAll App"

        //initiate database
        var helper = Database(applicationContext)
        var db = helper.readableDatabase


        //go to activity Cart. Here you will make virtual purchase and store it in database
        btnCart.setOnClickListener {
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }

        //Get back search result and fill it in main page center as a list view
        //The code below only calls the camera to scan the barcode, but the actual search query
        //will be down below under onActivityResult
        btnLookup.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@MainActivity)
            //if you wish for sound, change to true
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }

        //Close out of app by connecting it to a button
        btnCloseApp.setOnClickListener {
            this@MainActivity.finish()
            exitProcess(0)
        }


        //Create a button to another activity, that will update Product if it exists, and add if it doesn't
        //Items searched will appear with a description which is either Default, or what was entered by user above.
    }

    //Camera Functionality
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val db = Database(applicationContext).readableDatabase

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("MainActivity", "Scanned")
                Toast.makeText(this, "Scanned -> " + result.contents, Toast.LENGTH_SHORT)
                        .show()

                //Here we will create a way to query and search database for the barcode scanned
                var info = result.contents

                //ORDER --> 0,1,2,3
                val cursor = db.rawQuery("SELECT RECEIPT.ORDER_ID, RECEIPT.ODATE, RECEIPT.STORE_NAME, RECEIPT_ITEM.P_BARCODE FROM RECEIPT INNER JOIN RECEIPT_ITEM ON RECEIPT.ORDER_ID = RECEIPT_ITEM.ORDER_ID",null)
                cursor.moveToFirst()
                var answer = ""
                var odate = cursor.getString(1)
                var store = cursor.getString(2)
//answer = 1. P_BARCODE, ODATE, STORE_NAME
                if (cursor.getString(3)==info){
                    answer = "\nProduct: $info\nDate: $odate\nStore: $store"
                }
                while (cursor.moveToNext()){
                    odate = cursor.getString(1)
                    store = cursor.getString(2)
                    if (cursor.getString(3)==info){
                        answer = "\nProduct: $info\nDate: $odate\nStore: $store"
                    }
                }

                answer =  if(answer ==""){"\nNO PREVIOUS PURCHASE"} else{answer}
                txtViewResult.text = String.format("Result %s", answer)

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}