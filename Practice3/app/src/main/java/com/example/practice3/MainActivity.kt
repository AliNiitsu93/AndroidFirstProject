package com.example.practice3

import android.content.pm.PackageManager
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askForCameraPermission() }

        else{
            setupControls() }
    }

    private fun setupControls(){
        detector = BarcodeDetector.Builder(this@MainActivity).build()
        cameraSource = CameraSource.Builder(this@MainActivity, detector).setAutoFocusEnabled(true).build()
        cameraSurfaceView.holder.addCallback(surgaceCallBack)
    }

    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(android.Manifest.permission.CAMERA),requestCodeCameraPermission)
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
            TODO("Not yet implemented")
        }

        override fun receiveDetections(p0: Detector.Detections<Barcode>?) {
            TODO("Not yet implemented")
        }
    }
}