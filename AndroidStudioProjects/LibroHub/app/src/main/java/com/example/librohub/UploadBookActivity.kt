package com.example.librohub

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class UploadBookActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null
    private lateinit var ivPreview: ImageView

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            ivPreview.setImageURI(it)
            Toast.makeText(this, "Gambar terpilih!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_book)

        // Tampilkan nama perpustakaan di header
        val myLib = LibraryManager.getLibrary(this)
        findViewById<TextView>(R.id.tvLibNameHeader).text = myLib?.name ?: "Profil Perpustakaan"

        val etTitle = findViewById<EditText>(R.id.etBookTitle)
        val etAuthor = findViewById<EditText>(R.id.etBookAuthor)
        val etCategory = findViewById<EditText>(R.id.etBookCategory)
        val etStock = findViewById<EditText>(R.id.etBookStock)
        ivPreview = findViewById(R.id.ivBookCoverPreview)
        val btnPickImage = findViewById<Button>(R.id.btnPickBookImage)
        val btnSave = findViewById<Button>(R.id.btnSaveBook)

        // Bisa klik tombol atau klik kotak gambarnya langsung
        btnPickImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        
        ivPreview.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val category = etCategory.text.toString()
            val stock = etStock.text.toString()

            if (myLib == null) {
                Toast.makeText(this, "Daftarkan perpustakaan Anda terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (title.isNotEmpty() && author.isNotEmpty()) {
                val savedImagePath = saveImageToInternalStorage(selectedImageUri)
                val newBook = LibraryManager.Book(title, author, category, stock, savedImagePath)
                
                // Simpan ke database lokal per perpustakaan
                LibraryManager.addBook(this, myLib.name, newBook)

                Toast.makeText(this, "Buku '$title' berhasil diunggah!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Judul dan Penulis harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri?): String? {
        if (uri == null) return null
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val fileName = "book_${System.currentTimeMillis()}.jpg"
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}