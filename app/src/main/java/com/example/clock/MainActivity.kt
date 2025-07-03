package com.example.clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clock.ui.theme.ClockMobileTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockMobileTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun getSharedGradient(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3F51B5), // Blue
            Color(0xFF673AB7), // Purple
            Color(0x6B6C43FF)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val darkGreen = Color(0xFF2F3D3F) // Dark gray-green like Casio
    val violet = Color(0xFF3F51B5)
    val lightViolet = Color(0xFF673AB7)
    val darkBlue = Color(0x6B6C43FF)
    val green = Color(0xFF90EE90) // Light green LCD color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = getSharedGradient())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Analog Clock",
            color = Color.Black,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        ModernAnalogClock(
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = lightViolet,
                    spotColor = lightViolet
                ),
            color = lightViolet
        )

        Spacer(modifier = Modifier.height(32.dp))


        //Add logic to choose between
        //AdvancedCasioDigitalClock()
        //DigitalTimeDateComposable()
        CasioDigitalTimeDateComposable(lightViolet)
    }
}

@Composable
fun DigitalTimeDateComposable() {
    var now by remember { mutableStateOf(Date()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = Date()
            delay(1000)
        }
    }
    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val timeString = timeFormat.format(now)
    val dateString = dateFormat.format(now)
    Text(
        "$timeString - $dateString",
        color = Color.Black,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
    )
}

