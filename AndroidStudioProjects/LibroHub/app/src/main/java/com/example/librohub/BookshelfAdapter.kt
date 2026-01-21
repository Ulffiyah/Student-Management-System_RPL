package com.example.librohub

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BookshelfAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookshelfAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvBookTitle)
        val author: TextView = itemView.findViewById(R.id.tvBookAuthor)
        val cover: ImageView = itemView.findViewById(R.id.ivBookCover)
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)
        val btnKeRakBuku: Button = itemView.findViewById(R.id.btnKeRakBuku)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        val context = holder.itemView.context

        holder.title.text = book.title
        holder.author.text = book.author
        
        if (book.imageResId != 0) {
            holder.cover.setImageResource(book.imageResId)
        } else {
            holder.cover.setImageResource(R.drawable.ic_launcher_background) // Fallback image
        }

        // --- Click Listeners ---
        val openDetail = {
            val intent = Intent(context, BookDetailsActivity::class.java).apply {
                putExtra("BOOK_NAME", book.title)
                putExtra("BOOK_AUTHOR", book.author)
                putExtra("BOOK_IMAGE_RES_ID", book.imageResId)
            }
            context.startActivity(intent)
        }

        holder.itemView.setOnClickListener { openDetail() }
        holder.btnDetail.setOnClickListener { openDetail() }
        
        holder.btnKeRakBuku.text = "Hapus" // Di rak buku, tombol ini berfungsi untuk menghapus
        holder.btnKeRakBuku.setOnClickListener {
            // Logika untuk menghapus buku akan ditambahkan nanti
            Toast.makeText(context, "Fitur Hapus akan segera hadir", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = books.size
}
