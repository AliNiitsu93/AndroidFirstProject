package com.example.practice5

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_cart.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


//private lateinit var listView ListView
class Cart : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // another list to populate second list view




//        if (cursor.moveToFirst()){ result = cursor.getString(cursor.getColumnIndex("PRODUCT.P_BARCODE")) }


        //Setting up the UI for Date. This will retrieve today's date automatically
        //unless you type it out manually
        val context = this
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        //val d = String.format("%s-%s-%s",month,day,year)
        //val date = LocalDate.parse(d, DateTimeFormatter.ISO_DATE)
        editDate.setText(String.format("%s-%s-%s",month,day,year))



        //USE THE FUNCTION TO EXTRACT DATA FROM
        //var list2 = dataToArray()
        //var list2 = MyApplication.cartList




        // initialize array adapter
        val adapter2:ArrayAdapter<String> = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            MyApplication.cartList
        )

        // attach the array adapter with list view
        listView2.adapter = adapter2


        //list view item click listener
        //when item clicked allow deletion
        listView2.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val clickedItem = parent.getItemAtPosition(position).toString()

            // show an alert dialog to confirm
            MaterialAlertDialogBuilder(context).apply {
                setTitle("Item: $clickedItem")
                setMessage("Are you want to delete it from list view?")
                setPositiveButton("Delete"){_,_->
                    // remove clicked item from list view
                    MyApplication.cartList.removeAt(position)
                    adapter2.notifyDataSetChanged()
                }
                setNeutralButton("Cancel"){_,_->}
            }.create().show()
        }


        //Setup barcode scan item into array
        btnAddItem.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@Cart)
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        //    adapter2.notifyDataSetChanged()
        }


        //Just a simple close the CART Activity. (Closing means to terminate)
        button3.setOnClickListener {
            MyApplication.cartList = mutableListOf<String>()
            finish()
        }

    }
    //Camera Functionality
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //result is the data received from IntentIntegrator (camera)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Cart", "Scanned")
                Toast.makeText(this, "Scanned -> " + result.contents, Toast.LENGTH_LONG)
                    .show()

                //Add item to global array list
                MyApplication.cartList.add(result.contents)


//                textView3.text = String.format("Scanned Result: %s", result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun dataToArray(): MutableList<String> {
        var helper = MyDBHelper(applicationContext)
        var db = helper.writableDatabase

        val list = mutableListOf<String>()
        var cursor = db.rawQuery("SELECT P_BARCODE FROM PRODUCT",null)
        var result = ""
        var rows = cursor.getCount()
        for (i in 1..rows){
            if (cursor.moveToNext())
                println("..................................................")
            result = cursor.getString(cursor.getColumnIndex("PRODUCT.P_BARCODE"))
            list.add(result)
        }

        return list
    }

}