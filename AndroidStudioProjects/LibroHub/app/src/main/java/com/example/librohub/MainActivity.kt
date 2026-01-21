package com.example.librohub

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerMain: ViewPager2
    private lateinit var ivNavBeranda: ImageView
    private lateinit var tvNavBeranda: TextView
    private lateinit var ivNavPencarian: ImageView
    private lateinit var tvNavPencarian: TextView
    private lateinit var ivNavRakBuku: ImageView
    private lateinit var tvNavRakBuku: TextView
    private lateinit var ivNavPinjaman: ImageView
    private lateinit var tvNavPinjaman: TextView
    private lateinit var ivNavProfil: ImageView
    private lateinit var tvNavProfil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewPagerMain = findViewById(R.id.viewPagerMain)
        
        ivNavBeranda = findViewById(R.id.ivNavBeranda)
        tvNavBeranda = findViewById(R.id.tvNavBeranda)
        ivNavPencarian = findViewById(R.id.ivNavPencarian)
        tvNavPencarian = findViewById(R.id.tvNavPencarian)
        ivNavRakBuku = findViewById(R.id.ivNavRakBuku)
        tvNavRakBuku = findViewById(R.id.tvNavRakBuku)
        ivNavPinjaman = findViewById(R.id.ivNavPinjaman)
        tvNavPinjaman = findViewById(R.id.tvNavPinjaman)
        ivNavProfil = findViewById(R.id.ivNavProfil)
        tvNavProfil = findViewById(R.id.tvNavProfil)

        val navBeranda = findViewById<View>(R.id.navBeranda)
        val navPencarian = findViewById<View>(R.id.navPencarian)
        val navRakBuku = findViewById<View>(R.id.navRakBuku)
        val navPinjaman = findViewById<View>(R.id.navPinjaman)
        val navProfil = findViewById<View>(R.id.navProfil)

        val adapter = MainSectionsPagerAdapter(this)
        viewPagerMain.adapter = adapter

        viewPagerMain.isUserInputEnabled = true

        viewPagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateBottomNavUI(position)
            }
        })

        navBeranda.setOnClickListener { viewPagerMain.currentItem = 0 }
        navPencarian.setOnClickListener { viewPagerMain.currentItem = 1 }
        navRakBuku.setOnClickListener { viewPagerMain.currentItem = 2 }
        navPinjaman.setOnClickListener { viewPagerMain.currentItem = 3 }
        navProfil.setOnClickListener { viewPagerMain.currentItem = 4 }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateBottomNavUI(position: Int) {
        val activeColor = ContextCompat.getColor(this, R.color.primary_dark)
        val inactiveColor = ContextCompat.getColor(this, R.color.gray_text)

        // Reset all to inactive
        ivNavBeranda.setColorFilter(inactiveColor)
        tvNavBeranda.setTextColor(inactiveColor)
        ivNavPencarian.setColorFilter(inactiveColor)
        tvNavPencarian.setTextColor(inactiveColor)
        ivNavRakBuku.setColorFilter(inactiveColor)
        tvNavRakBuku.setTextColor(inactiveColor)
        ivNavPinjaman.setColorFilter(inactiveColor)
        tvNavPinjaman.setTextColor(inactiveColor)
        ivNavProfil.setColorFilter(inactiveColor)
        tvNavProfil.setTextColor(inactiveColor)

        when (position) {
            0 -> {
                ivNavBeranda.setColorFilter(activeColor)
                tvNavBeranda.setTextColor(activeColor)
            }
            1 -> {
                ivNavPencarian.setColorFilter(activeColor)
                tvNavPencarian.setTextColor(activeColor)
            }
            2 -> {
                ivNavRakBuku.setColorFilter(activeColor)
                tvNavRakBuku.setTextColor(activeColor)
            }
            3 -> {
                ivNavPinjaman.setColorFilter(activeColor)
                tvNavPinjaman.setTextColor(activeColor)
            }
            4 -> {
                ivNavProfil.setColorFilter(activeColor)
                tvNavProfil.setTextColor(activeColor)
            }
        }
    }
}