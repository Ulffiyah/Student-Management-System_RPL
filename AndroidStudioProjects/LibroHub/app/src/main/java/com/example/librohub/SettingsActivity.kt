package com.example.librohub

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Menggunakan ImageButton karena di XML sudah diganti menjadi ImageButton
        val btnBack = findViewById<ImageButton>(R.id.btnBackFromSettings)
        btnBack.setOnClickListener {
            finish()
        }
    }
}