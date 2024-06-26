package ru.otus.animations

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

abstract class AnimatedView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    abstract val duration: Long

    private var started: Boolean = false

    protected val circles: List<Circle> by lazy {
        getAnimationCircles()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circles.forEach { canvas?.drawCircle(it) }
        invalidate()
    }

    fun animateCircles() = started.takeUnless { it }?.let {
        started = true
        startAnimation()
    }

    protected abstract fun startAnimation()

    abstract fun getAnimationCircles(): List<Circle>

}