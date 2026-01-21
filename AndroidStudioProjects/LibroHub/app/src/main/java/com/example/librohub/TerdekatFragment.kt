package com.example.librohub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class TerdekatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_terdekat, container, false)

        val cardLib1 = view.findViewById<MaterialCardView>(R.id.cardLib1)
        val cardLib2 = view.findViewById<MaterialCardView>(R.id.cardLib2)
        val cardLib3 = view.findViewById<MaterialCardView>(R.id.cardLib3)
        
        val cardBookHome1 = view.findViewById<MaterialCardView>(R.id.cardBookHome1)
        val btnKeRakBuku = view.findViewById<Button>(R.id.btnKeRakBuku)
        val btnDetailHome = view.findViewById<Button>(R.id.btnDetailHome)

        // --- Logika Klik Perpustakaan ---
        val openLibHome = { name: String, location: String ->
            val intent = Intent(requireContext(), LibraryHomeActivity::class.java)
            intent.putExtra("EXTRA_LIB_NAME", name)
            intent.putExtra("EXTRA_LIB_LOCATION", location)
            startActivity(intent)
        }

        cardLib1?.setOnClickListener { openLibHome("Perpustakaan SMAN 1 Lasalimu", "Jln. Poros Kamaru-baubau") }
        cardLib2?.setOnClickListener { openLibHome("Perpustakaan Kota Baubau", "Pusat Kota Baubau") }
        cardLib3?.setOnClickListener { openLibHome("Perpustakaan Desa Sukamaju", "Kecamatan Sukamaju") }

        // --- Logika Klik Buku Rekomendasi ---
        val bookWhyWeSleep = Book(title = "Why We Sleep", author = "Matthew Walker", rating = "Rating: 4.9/5", imageResId = 0) // Ganti 0 dengan gambar asli nanti

        val openBookDetail = { book: Book ->
            val intent = Intent(requireContext(), BookDetailsActivity::class.java)
            intent.putExtra("BOOK_NAME", book.title)
            intent.putExtra("BOOK_AUTHOR", book.author)
            intent.putExtra("BOOK_IMAGE_RES_ID", book.imageResId)
            startActivity(intent)
        }

        cardBookHome1?.setOnClickListener { openBookDetail(bookWhyWeSleep) }
        btnDetailHome?.setOnClickListener { openBookDetail(bookWhyWeSleep) }
        
        btnKeRakBuku?.setOnClickListener {
            BookshelfManager.addBook(requireContext(), bookWhyWeSleep)
        }

        return view
    }
}
