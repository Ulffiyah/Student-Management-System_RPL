package com.example.librohub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    
    private var currentImageResId = android.R.drawable.btn_star_big_on

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val ivProfileImage = findViewById<ImageView>(R.id.ivProfileImage)
        val tvName = findViewById<TextView>(R.id.tvProfileName)
        val tvEmail = findViewById<TextView>(R.id.tvProfileEmail)
        
        val btnRegisterLibrary = findViewById<Button>(R.id.btnRegisterLibrary)
        val btnMyLibrary = findViewById<Button>(R.id.btnMyLibrary)
        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        
        val navBeranda = findViewById<LinearLayout>(R.id.navBeranda)
        val navPencarian = findViewById<LinearLayout>(R.id.navPencarian)

        // Navigasi ke Daftar Ruang Perpus
        btnRegisterLibrary.setOnClickListener {
            val intent = Intent(this, RegisterLibraryActivity::class.java)
            startActivity(intent)
        }

        // PERBAIKAN: Menggunakan getLibrary(context) sebagai ganti registeredLibrary
        btnMyLibrary.setOnClickListener {
            val library = LibraryManager.getLibrary(this)
            if (library != null) {
                val intent = Intent(this, LibraryDetailsActivity::class.java)
                intent.putExtra("LIB_NAME", library.name)
                intent.putExtra("LIB_EMAIL", library.email)
                intent.putExtra("LIB_LOCATION", library.location)
                intent.putExtra("LIB_HOURS", library.hours)
                intent.putExtra("LIB_CONTACT", library.contact)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Kamu belum mendaftarkan perpustakaan", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigasi ke Pengaturan
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        btnEditProfile.setOnClickListener {
            showEditDialog(ivProfileImage, tvName, tvEmail)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        navBeranda.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        navPencarian.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showEditDialog(ivMainProfile: ImageView, tvName: TextView, tvEmail: TextView) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null)
        
        val ivEditImage = dialogView.findViewById<ImageView>(R.id.ivEditProfileImage)
        val btnChangePhoto = dialogView.findViewById<Button>(R.id.btnChangePhoto)
        val etName = dialogView.findViewById<EditText>(R.id.etEditName)
        val etEmail = dialogView.findViewById<EditText>(R.id.etEditEmail)

        ivEditImage?.setImageResource(currentImageResId)
        etName?.setText(tvName.text)
        etEmail?.setText(tvEmail.text)

        var tempImageResId = currentImageResId

        btnChangePhoto?.setOnClickListener {
            tempImageResId = if (tempImageResId == android.R.drawable.btn_star_big_on) {
                android.R.drawable.ic_menu_myplaces
            } else {
                android.R.drawable.btn_star_big_on
            }
            ivEditImage?.setImageResource(tempImageResId)
        }

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                currentImageResId = tempImageResId
                ivMainProfile.setImageResource(currentImageResId)
                tvName.text = etName?.text.toString()
                tvEmail.text = etEmail?.text.toString()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}