package com.example.clock

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.*
import kotlin.math.*

@Composable
fun ModernAnalogClock(
    modifier: Modifier = Modifier,
    clockSize: Dp = 300.dp
) {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentTime
    }

    val hours = calendar.get(Calendar.HOUR) % 12
    val minutes = calendar.get(Calendar.MINUTE)
    val seconds = calendar.get(Calendar.SECOND)

    // Calculate angles (0 degrees is at 12 o'clock, clockwise)
    val secondAngle by animateFloatAsState(
        targetValue = seconds * 6f, // 360/60 = 6 degrees per second
        animationSpec = tween(durationMillis = if (seconds == 0) 0 else 1000),
        label = "second_hand"
    )

    val minuteAngle by animateFloatAsState(
        targetValue = minutes * 6f + seconds * 0.1f, // 360/60 = 6 degrees per minute + smooth transition
        animationSpec = tween(durationMillis = 1000),
        label = "minute_hand"
    )

    val hourAngle by animateFloatAsState(
        targetValue = hours * 30f + minutes * 0.5f, // 360/12 = 30 degrees per hour + smooth transition
        animationSpec = tween(durationMillis = 1000),
        label = "hour_hand"
    )

    Box(
        modifier = modifier.size(clockSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = minOf(size.width, size.height) / 2 - 20.dp.toPx()

            // Draw outer ring with gradient
            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF2E2E2E),
                        Color(0xFF1A1A1A)
                    )
                ),
                radius = radius,
                center = center,
                style = Stroke(width = 8.dp.toPx())
            )

            // Draw inner shadow circle
            drawCircle(
                color = Color(0xFF0D0D0D),
                radius = radius - 4.dp.toPx(),
                center = center
            )

            // Draw hour markers
            for (i in 1..12) {
                val angle = (i * 30 - 90) * PI / 180 // Convert to radians, -90 to start from top
                val startRadius = radius - 30.dp.toPx()
                val endRadius = radius - 10.dp.toPx()

                val startX = center.x + cos(angle).toFloat() * startRadius
                val startY = center.y + sin(angle).toFloat() * startRadius
                val endX = center.x + cos(angle).toFloat() * endRadius
                val endY = center.y + sin(angle).toFloat() * endRadius

                drawLine(
                    color = Color(0xFF4CAF50),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Draw minute markers
            for (i in 1..60) {
                if (i % 5 != 0) { // Skip hour markers
                    val angle = (i * 6 - 90) * PI / 180
                    val startRadius = radius - 20.dp.toPx()
                    val endRadius = radius - 10.dp.toPx()

                    val startX = center.x + cos(angle).toFloat() * startRadius
                    val startY = center.y + sin(angle).toFloat() * startRadius
                    val endX = center.x + cos(angle).toFloat() * endRadius
                    val endY = center.y + sin(angle).toFloat() * endRadius

                    drawLine(
                        color = Color(0xFF666666),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }

            // Draw hour hand
            val hourAngleRad = (hourAngle - 90) * PI / 180
            val hourHandLength = radius * 0.5f
            val hourEndX = center.x + cos(hourAngleRad).toFloat() * hourHandLength
            val hourEndY = center.y + sin(hourAngleRad).toFloat() * hourHandLength

            drawLine(
                color = Color.White,
                start = center,
                end = Offset(hourEndX, hourEndY),
                strokeWidth = 8.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Draw minute hand
            val minuteAngleRad = (minuteAngle - 90) * PI / 180
            val minuteHandLength = radius * 0.7f
            val minuteEndX = center.x + cos(minuteAngleRad).toFloat() * minuteHandLength
            val minuteEndY = center.y + sin(minuteAngleRad).toFloat() * minuteHandLength

            drawLine(
                color = Color(0xFF4CAF50),
                start = center,
                end = Offset(minuteEndX, minuteEndY),
                strokeWidth = 6.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Draw second hand
            val secondAngleRad = (secondAngle - 90) * PI / 180
            val secondHandLength = radius * 0.8f
            val secondEndX = center.x + cos(secondAngleRad).toFloat() * secondHandLength
            val secondEndY = center.y + sin(secondAngleRad).toFloat() * secondHandLength

            // Second hand with tail
            val tailLength = radius * 0.2f
            val tailEndX = center.x - cos(secondAngleRad).toFloat() * tailLength
            val tailEndY = center.y - sin(secondAngleRad).toFloat() * tailLength

            drawLine(
                color = Color(0xFFFF5722),
                start = Offset(tailEndX, tailEndY),
                end = Offset(secondEndX, secondEndY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Draw center dot
            drawCircle(
                color = Color.White,
                radius = 12.dp.toPx(),
                center = center
            )

            drawCircle(
                color = Color(0xFF4CAF50),
                radius = 8.dp.toPx(),
                center = center
            )

            drawCircle(
                color = Color(0xFFFF5722),
                radius = 4.dp.toPx(),
                center = center
            )
        }


    }

    Box(
        contentAlignment = Alignment.Center
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        // Digital time display at bottom
        Text(
            text = String.format(Locale.ROOT, "%02d:%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY),
                minutes,
                seconds
            ),
            color = Color(0xFF4CAF50),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
        )
    }
}