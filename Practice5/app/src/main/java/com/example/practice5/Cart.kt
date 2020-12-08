package com.example.practice5

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_cart.*

import java.util.*


class Cart : AppCompatActivity() {
    private val PERMISSION_CODE= 1000
    private val IMAGE_CAPTURE_CODE=1001
    var image_rui: Uri? = null

    val context = this

    // initialize array adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

//        if (cursor.moveToFirst()){ result = cursor.getString(cursor.getColumnIndex("PRODUCT.P_BARCODE")) }


        //Setting up the UI for Date. This will retrieve today's date automatically
        //unless you type it out manually
//        val context = this
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


        // This will initiate the view and allow the deleteability function of your listview
        val adapter2:ArrayAdapter<String> =
            ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, MyApplication.cartList)

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
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }

        //


        //Just a simple close the CART Activity. (Closing means to terminate)
        button3.setOnClickListener {
            MyApplication.cartList = mutableListOf<String>()
            finish()
        }

        btnFinish.setOnClickListener {
            //Take a picture of barcode and save it
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                //On click, go first to camera to take a picture of receipt barcode
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                    //Permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission,PERMISSION_CODE)
                }
                else{
                    //Permission already granted
                    openCamera()
                }
            }
            else{
                openCamera()
            }


            //Store into DATABASE
        }

    }
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_rui = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)


        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui)
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses allow or deny from permission request popup
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission Denied",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    //When the camera is activated, it will look for a result  which can be handled in this code below
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            //set image capture to image view
            image_view.setImageURI(image_rui)
        }
        else {
            println("I'M IN EELLLLLSEEE RESULT_________________________________________________________")

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


                    //recreate the listview as a means to update list on Add
                    createView()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }



    //This will allow us to update the listview every time an item is placed into the array
    fun createView(){
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
    }

}