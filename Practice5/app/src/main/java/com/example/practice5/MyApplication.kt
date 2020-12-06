package com.example.practice5

import android.app.Application

class MyApplication: Application() {
    companion object {
        var globalVar = 2
        var cartList = mutableListOf<String>()

    }

    override fun onCreate() {
        super.onCreate()
        // initialization code here
    }
}