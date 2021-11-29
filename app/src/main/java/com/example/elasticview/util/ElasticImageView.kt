package com.example.elasticview.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatImageView
import com.example.elasticview.R

@Suppress("unused")
class ElasticImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle), ElasticInterface {

  /** The target elastic scale size of the animation. */
  var scale = Definitions.DEFAULT_SCALE

  /** The default duration of the animation. */
  var duration = Definitions.DEFAULT_DURATION

  private var onClickListener: OnClickListener? = null
  private var onFinishListener: ElasticFinishListener? = null

  init {
    onCreate()
    when {
      attrs != null && defStyle != 0 -> getAttrs(attrs, defStyle)
      attrs != null -> getAttrs(attrs)
    }
  }

  private fun onCreate() {
    this.isClickable = true
    super.setOnClickListener {
      elasticAnimation(this) {
        setDuration(this@ElasticImageView.duration)
        setScaleX(this@ElasticImageView.scale)
        setScaleY(this@ElasticImageView.scale)
        setOnFinishListener { invokeListeners() }
      }.doAction()
    }
  }

  private fun getAttrs(attrs: AttributeSet) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ElasticImageView)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
    val typedArray =
      context.obtainStyledAttributes(attrs, R.styleable.ElasticImageView, defStyle, 0)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun setTypeArray(typedArray: TypedArray) {
    this.scale = typedArray.getFloat(R.styleable.ElasticImageView_imageView_scale, scale)
    this.duration = typedArray.getInt(R.styleable.ElasticImageView_imageView_duration, duration)
  }

  override fun setOnClickListener(listener: OnClickListener?) {
    this.onClickListener = listener
  }

  override fun setOnFinishListener(listener: ElasticFinishListener?) {
    this.onFinishListener = listener
  }

  override fun setOnClickListener(block: (View) -> Unit) =
    setOnClickListener(OnClickListener(block))

  override fun setOnFinishListener(block: () -> Unit) =
    setOnFinishListener(ElasticFinishListener(block))

  private fun invokeListeners() {
    this.onClickListener?.onClick(this)
    this.onFinishListener?.onFinished()
  }

  /** Builder class for creating [ElasticImageView]. */
  class Builder(context: Context) {
    private val elasticImageView = ElasticImageView(context)

    fun setScale(value: Float) = apply { this.elasticImageView.scale = value }
    fun setDuration(value: Int) = apply { this.elasticImageView.duration = value }

    @JvmSynthetic
    fun setOnClickListener(block: (View) -> Unit) = apply {
      setOnClickListener(OnClickListener(block))
    }

    fun setOnClickListener(value: OnClickListener) = apply {
      this.elasticImageView.setOnClickListener(value)
    }

    @JvmSynthetic
    fun setOnFinishListener(block: () -> Unit) = apply {
      setOnFinishListener(ElasticFinishListener(block))
    }

    fun setOnFinishListener(value: ElasticFinishListener) = apply {
      this.elasticImageView.setOnFinishListener(value)
    }

    fun build() = this.elasticImageView
  }
}
