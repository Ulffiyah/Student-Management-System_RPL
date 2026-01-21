package com.example.librohub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class LibraryHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_home)

        val libName = intent.getStringExtra("EXTRA_LIB_NAME") ?: "MENTARI PUSTAKA"
        val libLocation = intent.getStringExtra("EXTRA_LIB_LOCATION") ?: "SMA NEGERI 1 KEJOBONG"

        findViewById<TextView>(R.id.tvLibHomeName).text = libName
        findViewById<TextView>(R.id.tvLibHomeLocation).text = libLocation

        val booksContainer = findViewById<LinearLayout>(R.id.popularBooksContainer)
        val tvNoBooks = findViewById<TextView>(R.id.tvNoBooks)

        val booksFromLibrary = LibraryManager.getBooks(this, libName)

        if (booksFromLibrary.isEmpty()) {
            tvNoBooks.visibility = android.view.View.VISIBLE
        } else {
            tvNoBooks.visibility = android.view.View.GONE
            for (libBook in booksFromLibrary) {
                val itemView = LayoutInflater.from(this).inflate(R.layout.item_book, booksContainer, false)

                itemView.findViewById<TextView>(R.id.tvBookTitle).text = libBook.title
                itemView.findViewById<TextView>(R.id.tvBookAuthor).text = libBook.author

                val ivCover = itemView.findViewById<ImageView>(R.id.ivBookCover)
                if (libBook.imageUri != null) {
                    val file = File(libBook.imageUri)
                    if (file.exists()) {
                        ivCover.setImageURI(Uri.fromFile(file))
                    }
                }

                // --- Logika Tombol ---
                val btnDetail = itemView.findViewById<Button>(R.id.btnDetail)
                val btnKeRakBuku = itemView.findViewById<Button>(R.id.btnKeRakBuku)

                val bookToAdd = Book(title = libBook.title, author = libBook.author, imageResId = 0) // imageResId dari library book belum ada, jadi kita pakai 0

                val openBookDetail = {
                    val intent = Intent(this, BookDetailsActivity::class.java)
                    intent.putExtra("BOOK_NAME", bookToAdd.title)
                    intent.putExtra("BOOK_AUTHOR", bookToAdd.author)
                    startActivity(intent)
                }

                btnDetail.setOnClickListener { openBookDetail() }
                itemView.setOnClickListener { openBookDetail() }

                btnKeRakBuku.setOnClickListener {
                    BookshelfManager.addBook(this, bookToAdd)
                }

                booksContainer.addView(itemView)
            }
        }

        findViewById<Button>(R.id.btnBackLibHome).setOnClickListener {
            finish()
        }
    }
}
