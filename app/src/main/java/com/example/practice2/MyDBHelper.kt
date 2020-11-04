package com.example.practice2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper (context: Context): SQLiteOpenHelper(context, "USERDB", null, 1 ) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT)")
        db?.execSQL("INSERT INTO USERS(UNAME,PWD) VALUES('Ali@mnsu.edu','pcs')")
        db?.execSQL("INSERT INTO USERS(UNAME,PWD) VALUES('Ali@gmail.com','ATO')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

//class MyDBHelper(context: Context): SQLiteOpenHelper(context, name: "USERDB", factory: null, version: 1) {}