package com.example.clock

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun AnalogClockComposable(
    modifier: Modifier = Modifier,
    size: Dp = 240.dp
) {
    var calendar by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        while (true) {
            calendar = Calendar.getInstance()
            delay(1000)
        }
    }
    Canvas(modifier = modifier.then(Modifier.size(size))) {
        val width = size.toPx()
        val height = size.toPx()
        val radius = (width.coerceAtMost(height)) / 2 * 0.8f
        val center = Offset(width / 2, height / 2)

        // Draw clock face
        drawCircle(
            color = Color.Black,
            center = center,
            radius = radius,
            style = Stroke(width = 8f)
        )

        // Draw hour marks
        for (i in 0 until 12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val start = Offset(
                (center.x + Math.cos(angle) * (radius * 0.85)).toFloat(),
                (center.y + Math.sin(angle) * (radius * 0.85)).toFloat()
            )
            val end = Offset(
                (center.x + Math.cos(angle) * radius).toFloat(),
                (center.y + Math.sin(angle) * radius).toFloat()
            )
            drawLine(
                color = Color.Black,
                start = start,
                end = end,
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )
        }

        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        // Draw hour hand
        var angle = Math.toRadians(((hour + minute / 60.0) * 30 - 90))
        var handRadius = radius * 0.5f
        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(
                (center.x + Math.cos(angle) * handRadius).toFloat(),
                (center.y + Math.sin(angle) * handRadius).toFloat()
            ),
            strokeWidth = 12f,
            cap = StrokeCap.Round
        )

        // Draw minute hand
        angle = Math.toRadians(((minute + second / 60.0) * 6 - 90))
        handRadius = radius * 0.7f
        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(
                (center.x + Math.cos(angle) * handRadius).toFloat(),
                (center.y + Math.sin(angle) * handRadius).toFloat()
            ),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )

        // Draw second hand
        angle = Math.toRadians((second * 6 - 90).toDouble())
        handRadius = radius * 0.8f
        drawLine(
            color = Color.Red,
            start = center,
            end = Offset(
                (center.x + Math.cos(angle) * handRadius).toFloat(),
                (center.y + Math.sin(angle) * handRadius).toFloat()
            ),
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
    }
} 