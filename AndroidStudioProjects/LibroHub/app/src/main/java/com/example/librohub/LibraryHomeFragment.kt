package com.example.librohub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File

class LibraryHomeFragment : Fragment() {

    private lateinit var popularBooksContainer: LinearLayout
    private lateinit var tvNoBooks: TextView
    private var libName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lib_home, container, false)

        popularBooksContainer = view.findViewById(R.id.popularBooksContainer)
        tvNoBooks = view.findViewById(R.id.tvNoPopularBooks)

        // Mengambil data perpustakaan dari LibraryManager
        val library = LibraryManager.getLibrary(requireContext())
        
        val tvLibName = view.findViewById<TextView>(R.id.tvLibNameHome)
        val tvLibSub = view.findViewById<TextView>(R.id.tvLibSubHome)

        if (library != null) {
            libName = library.name
            tvLibName.text = library.name
            tvLibSub.text = library.location
        } else {
            libName = arguments?.getString("LIB_NAME")
            tvLibName.text = libName ?: "Nama Perpus"
        }

        view.findViewById<Button>(R.id.btnBackLibHome)?.setOnClickListener {
            activity?.finish()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        updatePopularBooksList()
    }

    private fun updatePopularBooksList() {
        popularBooksContainer.removeAllViews()
        
        val currentLibName = libName ?: "Nama Perpus"
        val books = LibraryManager.getBooks(requireContext(), currentLibName)

        if (books.isEmpty()) {
            tvNoBooks.visibility = View.VISIBLE
            if (popularBooksContainer.childCount == 0) {
                popularBooksContainer.addView(tvNoBooks)
            }
        } else {
            tvNoBooks.visibility = View.GONE
            for (book in books) {
                val bookItemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_library_book, popularBooksContainer, false)
                
                bookItemView.findViewById<TextView>(R.id.tvBookTitle).text = book.title
                bookItemView.findViewById<TextView>(R.id.tvBookAuthor).text = book.author
                
                val ivBookCover = bookItemView.findViewById<ImageView>(R.id.ivBookItemCover)
                if (book.imageUri != null) {
                    try {
                        val file = File(book.imageUri)
                        if (file.exists()) {
                            ivBookCover.setImageURI(Uri.fromFile(file))
                        }
                    } catch (e: Exception) {
                        ivBookCover.setImageResource(android.R.drawable.ic_menu_gallery)
                    }
                }

                // PERBAIKAN: Memastikan elemen root item bisa diklik
                val rootItem = bookItemView.findViewById<View>(R.id.rootBookItem)
                rootItem?.setOnClickListener {
                    val intent = Intent(requireContext(), BookDetailsActivity::class.java)
                    intent.putExtra("BOOK_NAME", book.title)
                    intent.putExtra("BOOK_RATING", "Rating 4,5%")
                    intent.putExtra("BOOK_NUMBER", "BN-" + System.currentTimeMillis().toString().takeLast(5))
                    intent.putExtra("LOAN_CODE", "LC-" + System.currentTimeMillis().toString().takeLast(6))
                    intent.putExtra("BOOK_IMAGE_URI", book.imageUri)
                    startActivity(intent)
                }
                
                popularBooksContainer.addView(bookItemView)
            }
        }
    }

    companion object {
        fun newInstance(name: String?): LibraryHomeFragment {
            val fragment = LibraryHomeFragment()
            val args = Bundle()
            args.putString("LIB_NAME", name)
            fragment.arguments = args
            return fragment
        }
    }
}