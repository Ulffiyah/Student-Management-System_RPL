package com.example.librohub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookshelfFragment : Fragment() {

    private lateinit var rvBookshelf: RecyclerView
    private lateinit var tvEmptyBookshelf: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookshelf, container, false)

        rvBookshelf = view.findViewById(R.id.rvBookshelf)
        tvEmptyBookshelf = view.findViewById(R.id.tvEmptyBookshelf)

        rvBookshelf.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()
        // Selalu perbarui daftar buku setiap kali halaman ini ditampilkan
        updateBookshelfView()
    }

    private fun updateBookshelfView() {
        val books = BookshelfManager.getBooks()

        if (books.isEmpty()) {
            rvBookshelf.visibility = View.GONE
            tvEmptyBookshelf.visibility = View.VISIBLE
        } else {
            rvBookshelf.visibility = View.VISIBLE
            tvEmptyBookshelf.visibility = View.GONE
            
            // Buat dan atur adapter baru dengan daftar buku terbaru
            val adapter = BookshelfAdapter(books)
            rvBookshelf.adapter = adapter
        }
    }
}
