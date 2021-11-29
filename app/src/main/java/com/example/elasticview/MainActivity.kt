package com.example.elasticview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttons(v: View) {
       when (v.id) {
            R.id.elasticbtn0 -> startActivity(Intent(baseContext, Activity1::class.java))
            R.id.elasticbtn1 -> startActivity(Intent(baseContext, Activity2::class.java))
            R.id.elasticbtn2 -> startActivity(Intent(baseContext, Activity3::class.java))
       }
    }
}

