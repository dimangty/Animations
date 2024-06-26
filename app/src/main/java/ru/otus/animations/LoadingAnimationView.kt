package ru.otus.animations

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import kotlin.math.absoluteValue
import kotlin.math.pow

class LoadingAnimationView(context: Context, attrs: AttributeSet) : AnimatedView(context, attrs) {

    override val duration = 1000L
    val size = 40F
    val radius = 40

    private val firstAnimation = ValueAnimator.ofFloat(-size, size).apply {
        duration = this@LoadingAnimationView.duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            val value = it.animatedValue as Float
            circles[0].x = width / 2 + toPixels(value)
            val max = size.pow(4) / 5
            circles[0].radius = toPixels(radius - value.pow(4) / max)
            circles[0].paint.alpha = 255
        }
    }


    private val firstAnimation1 = ValueAnimator.ofFloat(size, -size).apply {
        duration = this@LoadingAnimationView.duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            val value = it.animatedValue as Float
            circles[1].x = width / 2 + toPixels(value)
            val midle: Float = size * 0.45F
            circles[1].radius = toPixels(midle + value.absoluteValue * midle / size)
            circles[1].paint.alpha = (value.absoluteValue.pow(2) * 255 / 1225).toInt()
        }
    }

    private val secondAnimation = ValueAnimator.ofFloat(-size,size).apply {
        duration = this@LoadingAnimationView.duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            val value = it.animatedValue as Float
            circles[1].x = width / 2 + toPixels(value)
            val max = size.pow(4) / 5
            circles[1].radius = toPixels(radius - value.pow(4) / max)
            circles[1].paint.alpha = 255
        }
    }

    private val secondAnimation1 = ValueAnimator.ofFloat(size, -size).apply {
        duration = this@LoadingAnimationView.duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            val value = it.animatedValue as Float
            circles[0].x = width / 2 + toPixels(value)
            val x = circles[0].x
            circles[0].radius = toPixels(radius - 5F)
            circles[0].paint.alpha = 255
        }
    }





    override fun getAnimationCircles(): List<Circle> = listOf(
        Circle(
            x = width / 2 - toPixels(size),
            y = height / 2F,
            radius = toPixels(30F),
            paint = Paint().apply {
                style = Paint.Style.FILL
                color = Color.rgb(124, 79, 255)
                alpha = 255
            }
        ),
        Circle(
            x = width / 2 + toPixels(size),
            y = height / 2F,
            radius = toPixels(30F),
            paint = Paint().apply {
                style = Paint.Style.FILL
                color = Color.rgb(255, 57, 212)
                alpha = 255
            }
        )
    )

    override fun startAnimation() {
        AnimatorSet().apply {
            playSequentially(
                AnimatorSet().apply {
                    playTogether(firstAnimation, firstAnimation1)
                },
                AnimatorSet().apply {
                    playTogether(secondAnimation, secondAnimation1)
                }
            )
            addListener(
                onEnd = { it.start() }
            )
            start()
        }
    }

}