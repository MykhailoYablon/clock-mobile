package com.example.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import java.util.Calendar

class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            invalidate()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handler.post(updateRunnable)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(updateRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = (Math.min(width, height) / 2) * 0.8f
        val centerX = width / 2
        val centerY = height / 2

        // Draw clock face
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        paint.color = Color.BLACK
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Draw hour marks
        paint.strokeWidth = 6f
        for (i in 0 until 12) {
            val angle = Math.toRadians((i * 30).toDouble() - 90)
            val startX = (centerX + Math.cos(angle) * (radius * 0.85)).toFloat()
            val startY = (centerY + Math.sin(angle) * (radius * 0.85)).toFloat()
            val stopX = (centerX + Math.cos(angle) * radius).toFloat()
            val stopY = (centerY + Math.sin(angle) * radius).toFloat()
            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        // Draw hour hand
        paint.color = Color.BLACK
        paint.strokeWidth = 12f
        var angle = Math.toRadians(((hour + minute / 60.0) * 30 - 90))
        var handRadius = radius * 0.5f
        canvas.drawLine(centerX, centerY,
            (centerX + Math.cos(angle) * handRadius).toFloat(),
            (centerY + Math.sin(angle) * handRadius).toFloat(), paint)

        // Draw minute hand
        paint.strokeWidth = 8f
        angle = Math.toRadians(((minute + second / 60.0) * 6 - 90))
        handRadius = radius * 0.7f
        canvas.drawLine(centerX, centerY,
            (centerX + Math.cos(angle) * handRadius).toFloat(),
            (centerY + Math.sin(angle) * handRadius).toFloat(), paint)

        // Draw second hand
        paint.color = Color.RED
        paint.strokeWidth = 4f
        angle = Math.toRadians((second * 6 - 90).toDouble())
        handRadius = radius * 0.8f
        canvas.drawLine(centerX, centerY,
            (centerX + Math.cos(angle) * handRadius).toFloat(),
            (centerY + Math.sin(angle) * handRadius).toFloat(), paint)
    }
} 