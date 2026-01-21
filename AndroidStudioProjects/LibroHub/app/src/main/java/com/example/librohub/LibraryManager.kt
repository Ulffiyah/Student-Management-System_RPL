package com.example.librohub

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

object LibraryManager {
    private const val PREF_NAME = "LibraryPrefs"
    private const val KEY_NAME = "lib_name"
    private const val KEY_EMAIL = "lib_email"
    private const val KEY_LOCATION = "lib_location"
    private const val KEY_HOURS = "lib_hours"
    private const val KEY_CONTACT = "lib_contact"
    private const val KEY_BOOKS_PREFIX = "lib_books_"

    data class Library(
        val name: String,
        val email: String,
        val location: String,
        val hours: String,
        val contact: String
    )

    data class Book(
        val title: String,
        val author: String,
        val category: String,
        val stock: String,
        val imageUri: String? = null
    )

    fun saveLibrary(context: Context, library: Library) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_NAME, library.name)
            putString(KEY_EMAIL, library.email)
            putString(KEY_LOCATION, library.location)
            putString(KEY_HOURS, library.hours)
            putString(KEY_CONTACT, library.contact)
            apply()
        }
    }

    fun getLibrary(context: Context): Library? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString(KEY_NAME, null) ?: return null
        return Library(
            name,
            prefs.getString(KEY_EMAIL, "") ?: "",
            prefs.getString(KEY_LOCATION, "") ?: "",
            prefs.getString(KEY_HOURS, "") ?: "",
            prefs.getString(KEY_CONTACT, "") ?: ""
        )
    }

    fun addBook(context: Context, libName: String, book: Book) {
        val books = getBooks(context, libName).toMutableList()
        books.add(book)
        saveBooks(context, libName, books)
    }

    // Fungsi untuk memperbarui data buku (Edit)
    fun updateBook(context: Context, libName: String, oldTitle: String, updatedBook: Book) {
        val books = getBooks(context, libName).toMutableList()
        val index = books.indexOfFirst { it.title == oldTitle }
        if (index != -1) {
            books[index] = updatedBook
            saveBooks(context, libName, books)
        }
    }

    fun deleteBook(context: Context, libName: String, title: String) {
        val books = getBooks(context, libName).toMutableList()
        books.removeAll { it.title == title }
        saveBooks(context, libName, books)
    }

    private fun saveBooks(context: Context, libName: String, books: List<Book>) {
        val jsonArray = JSONArray()
        for (b in books) {
            val bookObj = JSONObject()
            bookObj.put("title", b.title)
            bookObj.put("author", b.author)
            bookObj.put("category", b.category)
            bookObj.put("stock", b.stock)
            bookObj.put("imageUri", b.imageUri)
            jsonArray.put(bookObj)
        }
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_BOOKS_PREFIX + libName, jsonArray.toString()).apply()
    }

    fun getBooks(context: Context, libName: String): List<Book> {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_BOOKS_PREFIX + libName, null)
        
        if (json == null) {
            return getDemoBooks(libName)
        }
        
        val books = mutableListOf<Book>()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val bookObj = jsonArray.getJSONObject(i)
            books.add(Book(
                bookObj.getString("title"),
                bookObj.getString("author"),
                bookObj.getString("category"),
                bookObj.getString("stock"),
                bookObj.optString("imageUri", null)
            ))
        }
        return books
    }

    private fun getDemoBooks(libName: String): List<Book> {
        return when (libName) {
            "Perpustakaan SMAN 1 Lasalimu" -> listOf(
                Book("Fisika Dasar", "Prof. Andi", "Sains", "10"),
                Book("Biologi Modern", "Dr. Siti", "Sains", "5")
            )
            "Perpustakaan Kota Baubau" -> listOf(
                Book("Sejarah Buton", "La Ode", "Sejarah", "3"),
                Book("Budaya Maritim", "Wakatobi Team", "Budaya", "7")
            )
            "Perpustakaan Desa Sukamaju" -> listOf(
                Book("Pertanian Organik", "Pak Tani", "Sains", "12"),
                Book("Beternak Lele", "Budi Daya", "Sains", "20")
            )
            "Perpustakaan Mentari Pustaka" -> listOf(
                Book("Ilmu Komputer 2", "Tech Expert", "Teknologi", "15")
            )
            else -> emptyList()
        }
    }
}