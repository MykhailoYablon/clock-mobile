package com.example.clock

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val digitalTimeText = findViewById<TextView>(R.id.digitalTimeText)
        val handler = Handler(Looper.getMainLooper())
        val timeRunnable = object : Runnable {
            override fun run() {
                val now = Calendar.getInstance().time
                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val timeString = timeFormat.format(now)
                val dateString = dateFormat.format(now)
                digitalTimeText.text = "$timeString - $dateString"
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(timeRunnable)
    }
}