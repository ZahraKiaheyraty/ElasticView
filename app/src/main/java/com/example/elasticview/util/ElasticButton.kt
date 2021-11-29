package com.example.elasticview.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatButton
import com.example.elasticview.R
import com.example.elasticview.util.Definitions.DEFAULT_DURATION
import com.example.elasticview.util.Definitions.DEFAULT_SCALE

@Suppress("unused")
class ElasticButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = androidx.appcompat.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyle), ElasticInterface {

  /** The target elastic scale size of the animation. */
  var scale = DEFAULT_SCALE

  /** The default duration of the animation. */
  var duration = DEFAULT_DURATION

  @Px
  var cornerRadius = 0f

  private var onClickListener: OnClickListener? = null
  private var onFinishListener: ElasticFinishListener? = null

  init {
    onCreate()
    when {
      attrs != null && defStyle != androidx.appcompat.R.attr.buttonStyle ->
        getAttrs(attrs, defStyle)
      attrs != null -> getAttrs(attrs)
    }
  }

  private fun onCreate() {
    this.isAllCaps = false
    super.setOnClickListener {
      elasticAnimation(this) {
        setDuration(this@ElasticButton.duration)
        setScaleX(this@ElasticButton.scale)
        setScaleY(this@ElasticButton.scale)
        setOnFinishListener { invokeListeners() }
      }.doAction()
    }
  }

  private fun getAttrs(attrs: AttributeSet) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ElasticButton)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ElasticButton, defStyle, 0)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun setTypeArray(typedArray: TypedArray) {
    this.scale = typedArray.getFloat(R.styleable.ElasticButton_button_scale, this.scale)
    this.duration = typedArray.getInt(R.styleable.ElasticButton_button_duration, this.duration)
    this.cornerRadius =
      typedArray.getDimension(R.styleable.ElasticButton_button_cornerRadius, this.cornerRadius)
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    initializeBackground()
  }

  private fun initializeBackground() {
    if (background is ColorDrawable) {
      background = GradientDrawable().apply {
        cornerRadius = this@ElasticButton.cornerRadius
        setColor((background as ColorDrawable).color)
      }.mutate()
    }
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

  /** Builder class for creating [ElasticButton]. */
  class Builder(context: Context) {
    private val elasticButton = ElasticButton(context)

    fun setScale(value: Float) = apply { this.elasticButton.scale = value }
    fun setDuration(value: Int) = apply { this.elasticButton.duration = value }
    fun setCornerRadius(@Px value: Float) = apply { this.elasticButton.cornerRadius = value }

    @JvmSynthetic
    fun setOnClickListener(block: (View) -> Unit) = apply {
      setOnClickListener(OnClickListener(block))
    }

    fun setOnClickListener(value: OnClickListener) = apply {
      this.elasticButton.setOnClickListener(value)
    }

    @JvmSynthetic
    fun setOnFinishListener(block: () -> Unit) = apply {
      setOnFinishListener(ElasticFinishListener(block))
    }

    fun setOnFinishListener(value: ElasticFinishListener) = apply {
      this.elasticButton.setOnFinishListener(value)
    }

    fun build() = this.elasticButton
  }
}
