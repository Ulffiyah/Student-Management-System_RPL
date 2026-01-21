package com.example.librohub

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class LibraryDetailsActivity : AppCompatActivity() {

    private lateinit var viewPagerLib: ViewPager2
    private lateinit var ivNavLibBeranda: ImageView
    private lateinit var tvNavLibBeranda: TextView
    private lateinit var ivNavLibPencarian: ImageView
    private lateinit var tvNavLibPencarian: TextView
    private lateinit var ivNavLibProfil: ImageView
    private lateinit var tvNavLibProfil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_details)

        // Ambil data dari intent
        val libName = intent.getStringExtra("LIB_NAME")
        val libEmail = intent.getStringExtra("LIB_EMAIL")
        val libLocation = intent.getStringExtra("LIB_LOCATION")
        val libHours = intent.getStringExtra("LIB_HOURS")
        val libContact = intent.getStringExtra("LIB_CONTACT")

        // Inisialisasi View
        viewPagerLib = findViewById(R.id.viewPagerLib)
        ivNavLibBeranda = findViewById(R.id.ivNavLibBeranda)
        tvNavLibBeranda = findViewById(R.id.tvNavLibBeranda)
        ivNavLibPencarian = findViewById(R.id.ivNavLibPencarian)
        tvNavLibPencarian = findViewById(R.id.tvNavLibPencarian)
        ivNavLibProfil = findViewById(R.id.ivNavLibProfil)
        tvNavLibProfil = findViewById(R.id.tvNavLibProfil)

        val navBeranda = findViewById<View>(R.id.navLibBeranda)
        val navPencarian = findViewById<View>(R.id.navLibPencarian)
        val navProfil = findViewById<View>(R.id.navLibProfil)

        // Setup Adapter
        val adapter = LibrarySectionsPagerAdapter(this, libName, libEmail, libLocation, libHours, libContact)
        viewPagerLib.adapter = adapter

        // Sync navigasi bawah dengan geseran layar
        viewPagerLib.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateBottomNavUI(position)
            }
        })

        // Klik menu bawah
        navBeranda.setOnClickListener { viewPagerLib.currentItem = 0 }
        navPencarian.setOnClickListener { viewPagerLib.currentItem = 1 }
        navProfil.setOnClickListener { viewPagerLib.currentItem = 2 }
    }

    private fun updateBottomNavUI(position: Int) {
        val activeColor = ContextCompat.getColor(this, R.color.primary_dark)
        val inactiveColor = ContextCompat.getColor(this, R.color.gray_text)

        // Reset semua ke warna tidak aktif
        ivNavLibBeranda.setColorFilter(inactiveColor)
        tvNavLibBeranda.setTextColor(inactiveColor)
        ivNavLibPencarian.setColorFilter(inactiveColor)
        tvNavLibPencarian.setTextColor(inactiveColor)
        ivNavLibProfil.setColorFilter(inactiveColor)
        tvNavLibProfil.setTextColor(inactiveColor)

        when (position) {
            0 -> { // Beranda Perpus
                ivNavLibBeranda.setColorFilter(activeColor)
                tvNavLibBeranda.setTextColor(activeColor)
            }
            1 -> { // Pencarian Perpus
                ivNavLibPencarian.setColorFilter(activeColor)
                tvNavLibPencarian.setTextColor(activeColor)
            }
            2 -> { // Profil Perpus
                ivNavLibProfil.setColorFilter(activeColor)
                tvNavLibProfil.setTextColor(activeColor)
            }
        }
    }
}