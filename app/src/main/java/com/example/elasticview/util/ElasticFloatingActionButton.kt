package com.example.elasticview.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import com.example.elasticview.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("unused")
class ElasticFloatingActionButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = com.google.android.material.R.attr.floatingActionButtonStyle
) : FloatingActionButton(context, attrs, defStyle), ElasticInterface {

  /** The target elastic scale size of the animation. */
  var scale = Definitions.DEFAULT_SCALE

  /** The default duration of the animation. */
  var duration = Definitions.DEFAULT_DURATION

  private var onClickListener: OnClickListener? = null
  private var onFinishListener: ElasticFinishListener? = null

  init {
    onCreate()
    when {
      attrs != null && defStyle != com.google.android.material.R.attr.floatingActionButtonStyle ->
        getAttrs(attrs, defStyle)
      attrs != null -> getAttrs(attrs)
    }
  }

  private fun onCreate() {
    this.isClickable = true
    super.setOnClickListener {
      elasticAnimation(this) {
        setDuration(this@ElasticFloatingActionButton.duration)
        setScaleX(this@ElasticFloatingActionButton.scale)
        setScaleY(this@ElasticFloatingActionButton.scale)
        setOnFinishListener { invokeListeners() }
      }.doAction()
    }
  }

  private fun getAttrs(attrs: AttributeSet) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ElasticFloatingActionButton)
    setTypeArray(typedArray)
  }

  private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
    val typedArray =
      context.obtainStyledAttributes(attrs, R.styleable.ElasticFloatingActionButton, defStyle, 0)
    setTypeArray(typedArray)
  }

  private fun setTypeArray(typedArray: TypedArray) {
    this.scale = typedArray.getFloat(R.styleable.ElasticFloatingActionButton_fabutton_scale, scale)
    this.duration =
      typedArray.getInt(R.styleable.ElasticFloatingActionButton_fabutton_duration, duration)
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

  /** Builder class for creating [ElasticFloatingActionButton]. */
  class Builder(context: Context) {
    private val elasticFloatingButton = ElasticFloatingActionButton(context)

    fun setScale(value: Float) = apply { this.elasticFloatingButton.scale = value }
    fun setDuration(value: Int) = apply { this.elasticFloatingButton.duration = value }

    @JvmSynthetic
    fun setOnClickListener(block: (View) -> Unit) = apply {
      setOnClickListener(OnClickListener(block))
    }

    fun setOnClickListener(value: OnClickListener) = apply {
      this.elasticFloatingButton.setOnClickListener(value)
    }

    @JvmSynthetic
    fun setOnFinishListener(block: () -> Unit) = apply {
      setOnFinishListener(ElasticFinishListener(block))
    }

    fun setOnFinishListener(value: ElasticFinishListener) = apply {
      this.elasticFloatingButton.setOnFinishListener(value)
    }

    fun build() = this.elasticFloatingButton
  }
}
