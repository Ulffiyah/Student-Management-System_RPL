package com.example.librohub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterLibraryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_library)

        val etLibName = findViewById<EditText>(R.id.etLibName)
        val etLibEmail = findViewById<EditText>(R.id.etLibEmail)
        val etLibLocation = findViewById<EditText>(R.id.etLibLocation)
        val etLibHours = findViewById<EditText>(R.id.etLibHours)
        val etLibContact = findViewById<EditText>(R.id.etLibContact)
        val btnRegisterLib = findViewById<Button>(R.id.btnRegisterLib)

        btnRegisterLib.setOnClickListener {
            val name = etLibName.text.toString()
            val email = etLibEmail.text.toString()
            val location = etLibLocation.text.toString()
            val hours = etLibHours.text.toString()
            val contact = etLibContact.text.toString()

            if (name.isNotEmpty()) {
                // Simpan data secara permanen menggunakan SharedPreferences melalui LibraryManager
                val newLib = LibraryManager.Library(name, email, location, hours, contact)
                LibraryManager.saveLibrary(this, newLib)

                Toast.makeText(this, "Pendaftaran Ruang Perpus Berhasil", Toast.LENGTH_SHORT).show()
                
                // Navigasi ke halaman detail perpustakaan
                val intent = Intent(this, LibraryDetailsActivity::class.java)
                intent.putExtra("LIB_NAME", name)
                intent.putExtra("LIB_EMAIL", email)
                intent.putExtra("LIB_LOCATION", location)
                intent.putExtra("LIB_HOURS", hours)
                intent.putExtra("LIB_CONTACT", contact)
                startActivity(intent)
                
                finish()
            } else {
                Toast.makeText(this, "Nama Perpustakaan harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}