package com.example.librohub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class LibrarySearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menggunakan layout search yang sudah ada atau yang baru jika diperlukan
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}