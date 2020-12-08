package com.example.practice5

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHelper (context: Context): SQLiteOpenHelper(context, "USERDB", null, 1 ) {
    override fun onCreate(db: SQLiteDatabase?) {
        //CREATE A TABLE WHERE primary key will be product barcode
        db?.execSQL("CREATE TABLE IF NOT EXISTS PRODUCT(P_BARCODE TEXT PRIMARY KEY , DESCRIPTION TEXT)")
        //db?.execSQL("CREATE TABLE IF NOT EXISTS STORE(STORE_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS ORDERS(ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP, STORE_ID INTEGER NOT NULL REFERENCES STORE(STORE_ID), ORDER_STATUS TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS ORDER_ITEM(ORDER_ID INTEGER NOT NULL REFERENCES ORDERS(ORDER_ID), P_BARCODE TEXT NOT NULL REFERENCES PRODUCT(P_BARCODE), PRIMARY KEY(ORDER_ID, P_BARCODE) )")


        //just some data added as default input
        db?.execSQL("INSERT INTO PRODUCT(P_BARCODE, DESCRIPTION) VALUES('09661975680','Kirkland 500ml Purified Water')")
        db?.execSQL("INSERT INTO PRODUCT(P_BARCODE, DESCRIPTION) VALUES('381371151035','Aveeno Daily Moisturizing Lotion 20fl oz')")
        db?.execSQL("INSERT INTO PRODUCT(P_BARCODE, DESCRIPTION) VALUES('667550822959','PocketBac Anti-Bacterial Hand gel 1fl oz')")


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}