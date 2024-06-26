package ru.otus.animations

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener

class RippleAnimationView(context: Context, attrs: AttributeSet) : AnimatedView(context, attrs) {

    override val duration = 1000L

    private val numberOfCircles = 4
    private val size = 30F
    private val maxSize = 80

    private fun getAnimation(index: Int, circle: Circle) =
        ValueAnimator.ofFloat(0F, size).apply {
            duration = this@RippleAnimationView.duration
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                val value = animator.animatedValue as Float
                circle.radius = toPixels(index * size + value)
                circle.paint.alpha =
                    index.takeIf { it == numberOfCircles - 1 }
                        ?.let { maxSize * (size - value) / size }
                        ?.toInt()
                        ?: maxSize
            }
        }

    override fun getAnimationCircles(): List<Circle> =
        (0 until numberOfCircles).map {
            Circle(
                x = width / 2F,
                y = height / 2F,
                radius = toPixels((numberOfCircles - 1 - it) * size),
                paint = Paint().apply {
                    style = Paint.Style.FILL
                    color = Color.rgb(70, 217, 224)
                    alpha = 80
                }
            )
        }

    override fun startAnimation() {
        AnimatorSet().apply {
            playTogether(circles.mapIndexed { index, circle -> getAnimation(index, circle) })
            addListener(
                onEnd = { it.start() }
            )
            start()
        }
    }
}