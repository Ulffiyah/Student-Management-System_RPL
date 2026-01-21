package com.example.librohub

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class SearchFragment : Fragment() {

    private lateinit var resultsContainer: LinearLayout
    private lateinit var tvNoResults: TextView
    private lateinit var etSearch: EditText
    private lateinit var tvResultsHeader: TextView

    // Daftar perpustakaan (Gabungan data asli dan demo)
    private val allLibraries = mutableListOf<Pair<String, String>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        
        resultsContainer = view.findViewById(R.id.searchResultsContainer)
        tvNoResults = view.findViewById(R.id.tvNoResults)
        etSearch = view.findViewById(R.id.etMainSearch)
        tvResultsHeader = view.findViewById(R.id.tvResultsHeader)

        setupSearchListener()
        
        return view
    }

    override fun onResume() {
        super.onResume()
        prepareLibraryData()
        // Kosongkan hasil saat pertama kali masuk halaman
        updateSearchResults("") 
    }

    private fun prepareLibraryData() {
        allLibraries.clear()
        
        val myLibrary = LibraryManager.getLibrary(requireContext())
        myLibrary?.let {
            allLibraries.add(Pair(it.name, it.location))
        }

        allLibraries.add(Pair("Perpustakaan SMAN 1 Lasalimu", "Jln. Poros Kamaru-baubau"))
        allLibraries.add(Pair("Perpustakaan Kota Baubau", "Pusat Kota Baubau"))
        allLibraries.add(Pair("Perpustakaan Desa Sukamaju", "Kecamatan Sukamaju"))
        allLibraries.add(Pair("Perpustakaan Mentari Pustaka", "SMA Negeri 1 Kejobong"))
    }

    private fun setupSearchListener() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchResults(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateSearchResults(query: String) {
        resultsContainer.removeAllViews()
        
        if (query.trim().isEmpty()) {
            // Sembunyikan semua jika tidak ada teks yang diketik
            tvNoResults.visibility = View.GONE
            tvResultsHeader.visibility = View.GONE
            return
        }

        tvResultsHeader.visibility = View.VISIBLE
        val filteredList = allLibraries.filter { it.first.contains(query, ignoreCase = true) }

        if (filteredList.isNotEmpty()) {
            tvNoResults.visibility = View.GONE
            for (lib in filteredList) {
                addSearchResultView(lib.first, lib.second)
            }
        } else {
            tvNoResults.text = "Perpustakaan '$query' tidak ditemukan"
            tvNoResults.visibility = View.VISIBLE
        }
    }

    private fun addSearchResultView(name: String, location: String) {
        val itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_search_result, resultsContainer, false)
        
        val tvName = itemView.findViewById<TextView>(R.id.tvSearchLibName)
        val tvLocation = itemView.findViewById<TextView>(R.id.tvSearchLibLocation)
        
        tvName.text = name
        tvLocation.text = location
        
        itemView.setOnClickListener {
            val intent = Intent(requireContext(), LibraryHomeActivity::class.java)
            intent.putExtra("EXTRA_LIB_NAME", name)
            intent.putExtra("EXTRA_LIB_LOCATION", location)
            startActivity(intent)
        }
        
        resultsContainer.addView(itemView)
    }
}