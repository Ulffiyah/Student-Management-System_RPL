package com.example.librohub

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LibrarySectionsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val libName: String?,
    private val libEmail: String?,
    private val libLocation: String?,
    private val libHours: String?,
    private val libContact: String?
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LibraryHomeFragment.newInstance(libName)
            1 -> LibrarySearchFragment()
            2 -> LibraryProfileFragment.newInstance(libName, libEmail, libLocation, libHours, libContact)
            else -> LibraryHomeFragment.newInstance(libName)
        }
    }
}