package com.example.practice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    ///create a list or storage
    val foodList = arrayListOf("Chinese Stir Fry", "Tuk", "Ramen", "Curry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ///Assign decide button what happens when clicked.
        /// in this case random is the variable
        ///Random.nextInt(X) gives a random number from 0 to X-1
        /// random = Random() is just setting the variable up to be of type Random
        decideBtn.setOnClickListener {
            val random = Random()
            val randomFood = random.nextInt(foodList.count())
            selectedFoodTxt.text = foodList[randomFood]
        }
        ///selectedFoodTxt is the text view which can be updated using the .text = " "

        ///addFoodTxt is the text input area that can be collected from (.text.toString() and
        // erased (.text.clear())
        addFoodBtn.setOnClickListener {
            val newFood = addFoodTxt.text.toString()
            foodList.add(newFood)
            addFoodTxt.text.clear()
        }

        removeFoodBtn.setOnClickListener {
            val food = selectedFoodTxt.text.toString()
            foodList.remove(food)
            decideBtn.performClick()
        }


    }
}