package com.example.librohub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream

class LibraryProfileFragment : Fragment() {

    private lateinit var myBooksContainer: LinearLayout
    private lateinit var tvNoBooks: TextView
    private var libName: String? = null

    private var ivEditBookPreview: ImageView? = null
    private var tempBookImageUri: Uri? = null

    private val pickBookImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            tempBookImageUri = it
            ivEditBookPreview?.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lib_profile, container, false)

        myBooksContainer = view.findViewById(R.id.myBooksContainer)
        tvNoBooks = view.findViewById(R.id.tvNoBooks)

        val library = LibraryManager.getLibrary(requireContext())

        val tvName = view.findViewById<TextView>(R.id.tvLibNameDetail)
        val tvEmail = view.findViewById<TextView>(R.id.tvLibEmailDetail)
        val tvLocation = view.findViewById<TextView>(R.id.tvLibLocationDetail)
        val tvHours = view.findViewById<TextView>(R.id.tvLibHoursDetail)
        val tvContact = view.findViewById<TextView>(R.id.tvLibContactDetail)

        if (library != null) {
            libName = library.name
            tvName.text = library.name
            tvEmail.text = library.email
            tvLocation.text = library.location
            tvHours.text = library.hours
            tvContact.text = library.contact
        } else {
            libName = arguments?.getString("LIB_NAME")
            tvName.text = libName ?: "Nama Perpus"
            tvEmail.text = arguments?.getString("LIB_EMAIL") ?: "Email"
            tvLocation.text = arguments?.getString("LIB_LOCATION") ?: "Lokasi"
            tvHours.text = arguments?.getString("LIB_HOURS") ?: "Jam Operasional"
            tvContact.text = arguments?.getString("LIB_CONTACT") ?: "Kontak"
        }

        view.findViewById<Button>(R.id.btnUploadBook)?.setOnClickListener {
            val intent = Intent(requireContext(), UploadBookActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.btnLoanInfo)?.setOnClickListener {
            val intent = Intent(requireContext(), BorrowerInfoActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        updateBooksList()
    }

    private fun updateBooksList() {
        myBooksContainer.removeAllViews()
        val currentLibName = libName ?: "Nama Perpus"
        val books = LibraryManager.getBooks(requireContext(), currentLibName)

        if (books.isEmpty()) {
            tvNoBooks.visibility = View.VISIBLE
            if (myBooksContainer.childCount == 0) myBooksContainer.addView(tvNoBooks)
        } else {
            tvNoBooks.visibility = View.GONE
            for (book in books) {
                val bookItemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_library_book, myBooksContainer, false)
                
                bookItemView.findViewById<TextView>(R.id.tvBookTitle).text = book.title
                bookItemView.findViewById<TextView>(R.id.tvBookAuthor).text = book.author
                
                val ivBookCover = bookItemView.findViewById<ImageView>(R.id.ivBookItemCover)
                if (book.imageUri != null) {
                    try {
                        val file = File(book.imageUri)
                        if (file.exists()) ivBookCover.setImageURI(Uri.fromFile(file))
                        else ivBookCover.setImageResource(android.R.drawable.ic_menu_gallery)
                    } catch (e: Exception) {
                        ivBookCover.setImageResource(android.R.drawable.ic_menu_gallery)
                    }
                }

                bookItemView.findViewById<ImageButton>(R.id.btnBookMenu).setOnClickListener { v ->
                    val popup = PopupMenu(requireContext(), v)
                    popup.menuInflater.inflate(R.menu.book_options_menu, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.action_edit -> {
                                showEditBookDialog(book)
                                true
                            }
                            R.id.action_delete -> {
                                LibraryManager.deleteBook(requireContext(), currentLibName, book.title)
                                updateBooksList()
                                Toast.makeText(requireContext(), "Buku '${book.title}' dihapus", Toast.LENGTH_SHORT).show()
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }
                myBooksContainer.addView(bookItemView)
            }
        }
    }

    private fun showEditBookDialog(book: LibraryManager.Book) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_book, null)
        
        ivEditBookPreview = dialogView.findViewById(R.id.ivEditBookCover)
        val btnChange = dialogView.findViewById<Button>(R.id.btnChangeBookPhoto)
        val etTitle = dialogView.findViewById<EditText>(R.id.etEditBookTitle)
        val etAuthor = dialogView.findViewById<EditText>(R.id.etEditBookAuthor)

        etTitle.setText(book.title)
        etAuthor.setText(book.author)
        if (book.imageUri != null) {
            ivEditBookPreview?.setImageURI(Uri.fromFile(File(book.imageUri)))
        }

        tempBookImageUri = null // Reset gambar sementara setiap buka dialog

        btnChange.setOnClickListener {
            pickBookImageLauncher.launch("image/*")
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val newTitle = etTitle.text.toString()
                val newAuthor = etAuthor.text.toString()
                val finalImagePath = if (tempBookImageUri != null) saveImageToInternalStorage(tempBookImageUri) else book.imageUri

                val updatedBook = LibraryManager.Book(newTitle, newAuthor, book.category, book.stock, finalImagePath)
                LibraryManager.updateBook(requireContext(), libName ?: "Nama Perpus", book.title, updatedBook)
                
                updateBooksList()
                Toast.makeText(requireContext(), "Buku diperbarui!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun saveImageToInternalStorage(uri: Uri?): String? {
        if (uri == null) return null
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = File(requireContext().filesDir, "book_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        fun newInstance(name: String?, email: String?, location: String?, hours: String?, contact: String?): LibraryProfileFragment {
            val fragment = LibraryProfileFragment()
            val args = Bundle()
            args.putString("LIB_NAME", name)
            args.putString("LIB_EMAIL", email)
            args.putString("LIB_LOCATION", location)
            args.putString("LIB_HOURS", hours)
            args.putString("LIB_CONTACT", contact)
            fragment.arguments = args
            return fragment
        }
    }
}