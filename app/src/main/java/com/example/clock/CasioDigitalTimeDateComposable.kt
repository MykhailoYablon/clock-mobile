package com.example.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CasioDigitalTimeDateComposable(digitsColor: Color) {
    var now by remember { mutableStateOf(Date()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = Date()
            delay(1000)
        }
    }
    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val dayFormat = remember { SimpleDateFormat("EEE", Locale.getDefault()) }

    val timeString = timeFormat.format(now)
    val dateString = dateFormat.format(now)
    val dayString = dayFormat.format(now).uppercase()

    Box(
        modifier = Modifier
//            .background(
//                color = Color(0xFF2F3D3F), // Dark gray-green like Casio
//                shape = RoundedCornerShape(8.dp)
//            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF673AB7),
                        Color(0xFF1A1A1A)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFF1A1A1A),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Inner display area
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFF1A1A1A),
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF1A1A1A),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 20.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Time display
                Text(
                    text = timeString,
                    color = digitsColor, // Light green LCD color
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace, // Monospace for that digital look
                    letterSpacing = 3.sp,
                    modifier = Modifier
                        .drawBehind {
                            // Add a subtle glow effect
                            drawRect(
                                color = digitsColor.copy(alpha = 0.1f),
                                size = size
                            )
                        }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Date and day display
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = dayString,
                        color = digitsColor.copy(alpha = 0.8f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "•",
                        color = digitsColor.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Text(
                        text = dateString,
                        color = digitsColor.copy(alpha = 0.8f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // Corner decorations (like on real Casio watches)
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = Color(0xFF1A1A1A),
                    shape = CircleShape
                )
                .align(Alignment.TopStart)
                .offset(x = 4.dp, y = 4.dp)
        )

        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = Color(0xFF1A1A1A),
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
                .offset(x = (-4).dp, y = 4.dp)
        )
    }
}