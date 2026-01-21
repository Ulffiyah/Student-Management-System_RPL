package com.example.librohub

import android.content.Context
import android.widget.Toast
import java.util.Date

/**
 * Data class untuk merepresentasikan buku yang sedang dipinjam.
 * Menggunakan objek Book dan menambahkan detail peminjaman.
 */
data class LoanedBook(
    val book: Book,
    val loanDate: Date,
    val returnDate: Date
)

/**
 * Singleton object untuk mengelola daftar buku yang dipinjam.
 */
object LoanManager {

    private val loanedBooks = mutableListOf<LoanedBook>()

    /**
     * Meminjam sebuah buku. Mencegah duplikat.
     */
    fun borrowBook(context: Context, book: Book) {
        if (loanedBooks.none { it.book.title == book.title }) {
            val loanDate = Date() // Tanggal hari ini
            val returnDate = Date(loanDate.time + 7 * 24 * 60 * 60 * 1000) // 7 hari dari sekarang
            loanedBooks.add(LoanedBook(book, loanDate, returnDate))
            Toast.makeText(context, "'${book.title}' berhasil dipinjam", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Anda sudah meminjam buku ini", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Mengembalikan sebuah buku.
     */
    fun returnBook(context: Context, bookTitle: String) {
        val removed = loanedBooks.removeAll { it.book.title == bookTitle }
        if (removed) {
            Toast.makeText(context, "Buku berhasil dikembalikan", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Mengambil semua buku yang sedang dipinjam.
     */
    fun getLoanedBooks(): List<LoanedBook> {
        return loanedBooks.toList()
    }
}
