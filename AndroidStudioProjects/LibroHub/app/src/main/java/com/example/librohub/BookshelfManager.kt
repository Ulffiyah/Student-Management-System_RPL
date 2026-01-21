package com.example.librohub

import android.content.Context
import android.widget.Toast

/**
 * Data class untuk merepresentasikan sebuah buku.
 */
data class Book(
    val title: String,
    val author: String,
    val rating: String? = null,
    val imageResId: Int = 0 // Menggunakan resource ID untuk gambar
)

/**
 * Singleton object untuk mengelola daftar buku di rak (bookshelf).
 */
object BookshelfManager {

    private val bookshelf = mutableListOf<Book>()

    /**
     * Menambahkan buku ke rak. Mencegah duplikat berdasarkan judul.
     */
    fun addBook(context: Context, book: Book) {
        if (bookshelf.none { it.title == book.title }) {
            bookshelf.add(book)
            Toast.makeText(context, "'${book.title}' telah ditambahkan ke Rak Buku", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "'${book.title}' sudah ada di Rak Buku", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Mengambil semua buku dari rak.
     */
    fun getBooks(): List<Book> {
        return bookshelf.toList()
    }

    /**
     * Menghapus semua buku dari rak (opsional, untuk testing atau reset).
     */
    fun clearBookshelf() {
        bookshelf.clear()
    }
}
