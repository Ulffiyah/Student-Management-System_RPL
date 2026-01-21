package com.example.librohub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class LoanAdapter(private val loanedBooks: MutableList<LoanedBook>) : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

    class LoanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvBookTitle)
        val author: TextView = itemView.findViewById(R.id.tvBookAuthor)
        val returnDate: TextView = itemView.findViewById(R.id.tvReturnDate)
        val cover: ImageView = itemView.findViewById(R.id.ivBookCover)
        val btnReturn: Button = itemView.findViewById(R.id.btnPengembalian)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loaned_book, parent, false)
        return LoanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val loanedBook = loanedBooks[position]
        val context = holder.itemView.context

        holder.title.text = loanedBook.book.title
        holder.author.text = loanedBook.book.author

        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        holder.returnDate.text = "Kembalikan sebelum: ${sdf.format(loanedBook.returnDate)}"

        if (loanedBook.book.imageResId != 0) {
            holder.cover.setImageResource(loanedBook.book.imageResId)
        } else {
            holder.cover.setImageResource(R.drawable.ic_launcher_background) // Fallback image
        }

        holder.btnReturn.setOnClickListener {
            LoanManager.returnBook(context, loanedBook.book.title)
            // Hapus item dari daftar dan perbarui adapter
            loanedBooks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, loanedBooks.size)
        }
    }

    override fun getItemCount() = loanedBooks.size
}
