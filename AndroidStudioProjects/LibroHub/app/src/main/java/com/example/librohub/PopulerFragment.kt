package com.example.librohub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class PopulerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_populer, container, false)

        // --- Definisi Buku ---
        val book1 = Book(title = "Atomic Habits", author = "James Clear", rating = "Rating: 4.9/5", imageResId = 0)
        val book2 = Book(title = "The Psychology of Money", author = "Morgan Housel", rating = "Rating: 4.8/5", imageResId = 0)
        val book3 = Book(title = "Modul Pelatihan Manajemen", author = "Author Name", rating = "Rating: 4.7/5", imageResId = 0)

        // --- Fungsi Helper untuk Aksi Klik ---
        val openBookDetail = { book: Book ->
            val intent = Intent(requireContext(), BookDetailsActivity::class.java)
            intent.putExtra("BOOK_NAME", book.title)
            intent.putExtra("BOOK_AUTHOR", book.author)
            intent.putExtra("BOOK_IMAGE_RES_ID", book.imageResId)
            startActivity(intent)
        }

        // --- Buku 1: Atomic Habits ---
        val card1 = view.findViewById<MaterialCardView>(R.id.cardPopular1)
        val btnKeRakBuku1 = view.findViewById<Button>(R.id.btnKeRakBukuPopuler1)
        val btnDetail1 = view.findViewById<Button>(R.id.btnDetailPopuler1)

        card1?.setOnClickListener { openBookDetail(book1) }
        btnDetail1?.setOnClickListener { openBookDetail(book1) }
        btnKeRakBuku1?.setOnClickListener { BookshelfManager.addBook(requireContext(), book1) }

        // --- Buku 2: The Psychology of Money ---
        val card2 = view.findViewById<MaterialCardView>(R.id.cardPopular2)
        val btnKeRakBuku2 = view.findViewById<Button>(R.id.btnKeRakBukuPopuler2)
        val btnDetail2 = view.findViewById<Button>(R.id.btnDetailPopuler2)

        card2?.setOnClickListener { openBookDetail(book2) }
        btnDetail2?.setOnClickListener { openBookDetail(book2) }
        btnKeRakBuku2?.setOnClickListener { BookshelfManager.addBook(requireContext(), book2) }

        // --- Buku 3: Modul Pelatihan Manajemen ---
        val card3 = view.findViewById<MaterialCardView>(R.id.cardPopular3)
        val btnKeRakBuku3 = view.findViewById<Button>(R.id.btnKeRakBukuPopuler3)
        val btnDetail3 = view.findViewById<Button>(R.id.btnDetailPopuler3)

        card3?.setOnClickListener { openBookDetail(book3) }
        btnDetail3?.setOnClickListener { openBookDetail(book3) }
        btnKeRakBuku3?.setOnClickListener { BookshelfManager.addBook(requireContext(), book3) }

        return view
    }
}
