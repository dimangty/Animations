package ru.otus.animations

import android.graphics.Canvas
import android.util.TypedValue
import android.view.View

fun View.toPixels(dp: Float): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )

fun Canvas.drawCircle(circle: Circle) =
    drawCircle(circle.x, circle.y, circle.radius, circle.paint)