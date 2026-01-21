package com.example.librohub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class BorrowerInfoActivity : AppCompatActivity() {

    private lateinit var borrowerInfoContainer: LinearLayout
    private lateinit var tvNoBorrowers: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrower_info)

        borrowerInfoContainer = findViewById(R.id.borrowerInfoContainer)
        tvNoBorrowers = findViewById(R.id.tvNoBorrowers)
    }

    override fun onResume() {
        super.onResume()
        // Selalu update daftar setiap kali halaman muncul
        updateBorrowerList()
    }

    private fun updateBorrowerList() {
        borrowerInfoContainer.removeAllViews()
        // FIX 1: Menggunakan metode yang benar untuk mengambil buku
        val loanedBooks = LoanManager.getLoanedBooks()

        if (loanedBooks.isEmpty()) {
            tvNoBorrowers.visibility = View.VISIBLE
        } else {
            tvNoBorrowers.visibility = View.GONE
            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            for (loanedBook in loanedBooks) {
                val itemView = LayoutInflater.from(this).inflate(R.layout.item_borrower_info, borrowerInfoContainer, false)

                // FIX 2: Menggunakan properti yang benar dari struktur data LoanedBook dan Book
                // Asumsi "Borrower Name" adalah penulis buku untuk saat ini
                itemView.findViewById<TextView>(R.id.tvBorrowerName).text = ": ${loanedBook.book.author}"
                itemView.findViewById<TextView>(R.id.tvBorrowedBookTitle).text = ": ${loanedBook.book.title}"
                itemView.findViewById<TextView>(R.id.tvBorrowedDate).text = ": ${sdf.format(loanedBook.loanDate)}"
                itemView.findViewById<TextView>(R.id.tvReturnDate).text = ": ${sdf.format(loanedBook.returnDate)}"

                // Kolom yang tidak ada di model data kita, bisa di-hide atau dihapus dari layout
                itemView.findViewById<TextView>(R.id.tvBorrowedBookNumber)?.visibility = View.GONE
                itemView.findViewById<TextView>(R.id.tvBorrowedLoanCode)?.visibility = View.GONE

                val btnConfirmReturn = itemView.findViewById<Button>(R.id.btnConfirmReturnItem)
                btnConfirmReturn.setOnClickListener {
                    // FIX 3: Menggunakan metode yang benar untuk mengembalikan buku
                    LoanManager.returnBook(this, loanedBook.book.title)
                    updateBorrowerList() // Refresh daftar
                }

                borrowerInfoContainer.addView(itemView)
            }
        }
    }
}
