package com.example.librohub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutHome)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerHome)

        // Adapter untuk sub-tabs (Terdekat, Kategori, Populer)
        val adapter = SubTabsPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Terdekat"
                1 -> "Kategori"
                2 -> "Populer"
                else -> null
            }
        }.attach()

        return view
    }
}