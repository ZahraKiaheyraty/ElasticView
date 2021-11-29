package com.example.elasticview

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elasticview.util.ElasticCheckButton
import com.google.android.material.snackbar.Snackbar

class Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity1)
  }

  fun checkButtons(v: View) {
    val elasticCheckButton = v as ElasticCheckButton
    Snackbar.make(
      v,
      "[Change checked state] " +
        elasticCheckButton.text.toString() +
        " : " +
        elasticCheckButton.isChecked,
      200
    )
      .setActionTextColor(Color.WHITE)
      .show()
  }

  fun layout(v: View) {
    Snackbar.make(v, "Pop-up likes 'TimePickerDialog'", 200).setActionTextColor(Color.WHITE).show()
  }

  fun imageViews(v: View) {
    when (v.id) {
      R.id.example0_ibtn_q_timeset01 ->
        Snackbar.make(
          v,
          "Alarm goes off between start-time and end-time", 200
        )
          .setActionTextColor(Color.WHITE)
          .show()
      R.id.example0_ibtn_q_timeset02 ->
        Snackbar.make(v, "This is time interval description", 200)
          .setActionTextColor(Color.WHITE)
          .show()
    }
  }

  fun addNewAlarm(v: View) {
    Toast.makeText(baseContext, "a new Alarm added!", Toast.LENGTH_SHORT).show()
    finish()
  }
}
