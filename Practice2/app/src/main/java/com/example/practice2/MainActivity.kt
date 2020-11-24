package com.example.practice2

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM USERS",null)

       // if (rs.moveToNext())
         //   Toast.makeText(applicationContext,rs.getString(1),Toast.LENGTH_LONG).show()

        button.setOnClickListener {
            var cv = ContentValues()
            cv.put("UName",addEmail.text.toString())
            cv.put("PWD",addPassword.text.toString())

            db.insert("USERS", null, cv)
            Toast.makeText(applicationContext, addEmail.text.toString().plus(" Safely Added"), Toast.LENGTH_SHORT).show()
            addEmail.setText("")
            addPassword.setText("")
            addEmail.requestFocus()
        }
    }
}