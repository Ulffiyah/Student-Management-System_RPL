package com.example.librohub

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SubTabsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TerdekatFragment()
            1 -> KategoriFragment()
            2 -> PopulerFragment()
            else -> TerdekatFragment()
        }
    }
}