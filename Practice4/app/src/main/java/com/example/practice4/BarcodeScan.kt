package com.example.practice4

import android.content.pm.PackageManager
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_barcode_scan.*

import android.Manifest
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

class BarcodeScan : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        //intent should be placed before calling the camera functionality so that it is registered
        //as a button to return back to cart if necessary
        btnToCart.setOnClickListener {
            val intent = Intent(this, ShoppingCart::class.java)
            //start the next activity
            startActivity(intent)
        }


        if(ContextCompat.checkSelfPermission(
                this@BarcodeScan,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askForCameraPermission() }
        else{
            setupControls() }


    }
    private fun setupControls(){
        detector = BarcodeDetector.Builder(this@BarcodeScan).build()
        cameraSource = CameraSource.Builder(this@BarcodeScan, detector).setAutoFocusEnabled(true).build()
        cameraSurfaceView.holder.addCallback(surgaceCallBack)
        detector.setProcessor(processor)
    }

    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(this@BarcodeScan,
            arrayOf(Manifest.permission.CAMERA),requestCodeCameraPermission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setupControls()
            }
            else{
                Toast.makeText(applicationContext,"Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val surgaceCallBack = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            cameraSource.stop()
        }

        override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
            try{
                cameraSource.start(surfaceHolder)

            }catch(exception: Exception){
                Toast.makeText(applicationContext,"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val processor = object : Detector.Processor<Barcode>{
        override fun release() {

        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
            if(detections != null && detections.detectedItems.isNotEmpty()){
                val qrCodes: SparseArray<Barcode> = detections.detectedItems
                val code = qrCodes.valueAt(0)
                val barcodeValue = code.displayValue
                textScanResult.text = barcodeValue

                //barcodeValue.toString() is the data needed to pull up information from database
                println("__________________________"+barcodeValue.toString())

                }
                //SUCCESS. collected barcode as a String

            else{
                textScanResult.text = ""
            }
        }
    }
}