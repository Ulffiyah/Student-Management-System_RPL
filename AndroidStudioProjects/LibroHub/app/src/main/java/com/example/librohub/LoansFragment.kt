package com.example.librohub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoansFragment : Fragment() {

    private lateinit var rvLoans: RecyclerView
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loans, container, false)
        rvLoans = view.findViewById(R.id.rvLoans)
        tvEmpty = view.findViewById(R.id.tvEmptyLoans)
        rvLoans.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onResume() {
        super.onResume()
        updateLoansList()
    }

    private fun updateLoansList() {
        val loanedBooks = LoanManager.getLoanedBooks().toMutableList()

        if (loanedBooks.isEmpty()) {
            rvLoans.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            rvLoans.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
            val adapter = LoanAdapter(loanedBooks)
            rvLoans.adapter = adapter
        }
    }
}
