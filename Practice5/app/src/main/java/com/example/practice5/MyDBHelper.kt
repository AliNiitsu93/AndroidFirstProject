package com.example.practice5

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHelper (context: Context): SQLiteOpenHelper(context, "USERDB", null, 1 ) {
    override fun onCreate(db: SQLiteDatabase?) {
        //CREATE A TABLE WHERE primary key will be product barcode
        //Drop tables if debugging and needing a new table

        db?.execSQL("CREATE TABLE ORDERS(ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP, ODATE TEXT, STORE_NAME TEXT)")
        db?.execSQL("CREATE TABLE ORDER_ITEM(ORDER_ID INTEGER NOT NULL REFERENCES ORDERS(ORDER_ID), P_BARCODE TEXT NOT NULL REFERENCES PRODUCT(P_BARCODE), PRIMARY KEY(ORDER_ID, P_BARCODE) )")
        db?.execSQL("CREATE TABLE PRODUCT(P_BARCODE TEXT PRIMARY KEY , DESCRIPTION TEXT)")


        //just some data added as default input
        db?.execSQL("INSERT INTO PRODUCT(P_BARCODE, DESCRIPTION) VALUES('09661975680','Kirkland 500ml Purified Water')")
        db?.execSQL("INSERT INTO PRODUCT(P_BARCODE, DESCRIPTION) VALUES('381371151035','Aveeno Daily Moisturizing Lotion 20fl oz')")
        db?.execSQL("INSERT INTO PRODUCT(P_BARCODE, DESCRIPTION) VALUES('667550822959','PocketBac Anti-Bacterial Hand gel 1fl oz')")
        db?.execSQL("INSERT INTO ORDERS(DATE, STORE_NAME) VALUES('11-1-2020', 'Walmart')")


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ORDER_ITEM")
        db?.execSQL("DROP TABLE IF EXISTS ORDERS")
        db?.execSQL("DROP TABLE IF EXISTS STORE")
        db?.execSQL("DROP TABLE IF EXISTS PRODUCT")
        onCreate(db)
    }
}