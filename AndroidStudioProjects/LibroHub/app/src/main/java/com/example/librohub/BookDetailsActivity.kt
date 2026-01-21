package com.example.librohub

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        // Mengambil data dari Intent
        val bookName = intent.getStringExtra("BOOK_NAME") ?: "Judul Tidak Diketahui"
        val bookAuthor = intent.getStringExtra("BOOK_AUTHOR") ?: "Penulis Tidak Diketahui"
        val bookRating = intent.getStringExtra("BOOK_RATING")
        val imageResId = intent.getIntExtra("BOOK_IMAGE_RES_ID", 0)
        
        // Menampilkan data di UI
        findViewById<TextView>(R.id.book_name_detail).text = bookName
        findViewById<TextView>(R.id.book_rating_detail).text = bookRating
        
        val bookCover = findViewById<ImageView>(R.id.book_cover_detail)
        if (imageResId != 0) {
            bookCover.setImageResource(imageResId)
        } else {
            bookCover.setImageResource(R.drawable.ic_launcher_background) // Gambar fallback
        }

        // Membuat objek Book untuk digunakan
        val currentBook = Book(bookName, bookAuthor, bookRating, imageResId)

        // --- Logika Tombol Pinjam ---
        val btnPinjam = findViewById<Button>(R.id.btnPinjam)
        btnPinjam.setOnClickListener {
            LoanManager.borrowBook(this, currentBook)
            // Opsional: Anda bisa menutup halaman ini setelah meminjam
            // finish()
        }
    }
}
