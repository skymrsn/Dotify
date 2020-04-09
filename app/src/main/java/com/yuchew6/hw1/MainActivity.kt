package com.yuchew6.hw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.random.Random
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val randomNumber = Random.nextInt(1000, 10000)
    var counter = randomNumber
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        clickCount.text = "${randomNumber.toString()} plays"

        btnChange.setOnClickListener {
            userName.visibility = View.INVISIBLE
            inputUserName.visibility = View.VISIBLE
            btnChange.visibility = View.INVISIBLE
            btnApply.visibility = View.VISIBLE
        }

        btnApply.setOnClickListener {
            if (inputUserName.text.toString() == "") {
                Toast.makeText(this, "Must input something", Toast.LENGTH_SHORT).show()
            } else {
                userName.text = inputUserName.text
                userName.visibility = View.VISIBLE
                inputUserName.visibility = View.INVISIBLE
                btnApply.visibility = View.INVISIBLE
                btnChange.visibility = View.VISIBLE
            }
        }

        btnPlay.setOnClickListener {
            counter += 1
            clickCount.text = "${counter.toString()} plays"
        }

        btnPrevious.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener {
            Toast.makeText(this, "Skipping to Next track", Toast.LENGTH_SHORT).show()
        }
    }
}
