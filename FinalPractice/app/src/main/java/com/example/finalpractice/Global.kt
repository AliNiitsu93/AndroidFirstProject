package com.example.finalpractice

import android.app.Application


class Global: Application() {
    companion object {
        var cartList = mutableListOf<String>()

    }

    override fun onCreate() {
        super.onCreate()
        // initialization code here
    }
}