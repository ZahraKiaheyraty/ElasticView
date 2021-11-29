package com.example.elasticview

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.elasticview.util.ElasticAnimation
import com.google.android.material.snackbar.Snackbar

class Activity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity3)
  }

  fun views(v: View) {
    when (v.id) {
      R.id.example2_view3 ->
        ElasticAnimation(v)
          .setScaleX(0.85f)
          .setScaleY(0.85f)
          .setDuration(500)
          .setOnFinishListener {

          }
          .doAction()
      R.id.example2_imv -> Snackbar.make(v, "This is ElasticImageView", 200).setActionTextColor(
        Color.WHITE
      ).show()
      R.id.example2_textView0 -> ElasticAnimation(v).setScaleX(0.75f).setScaleY(0.75f).setDuration(
        500
      ).doAction()
      R.id.example2_fab ->
        Snackbar.make(v, "This is ElasticFloatActionButton", 200)
          .setActionTextColor(Color.WHITE)
          .show()
    }
  }
}
