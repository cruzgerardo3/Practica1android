package com.example.practica1android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.red

class Esfera(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    private var x = 0f
    private var y = 0f


    fun updatePosition(newX: Float, newY: Float) {
        x = width / 2f + newX
        y = height / 2f + newY
        invalidate()
    }


    fun setColor(newColor: Int) {
        paint.color = newColor
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(x, y, 50f, paint)
    }
}
