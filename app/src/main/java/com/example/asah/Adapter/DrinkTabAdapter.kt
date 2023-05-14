package com.example.asah.Adapter

import android.app.ActivityManager
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.asah.DrinkFragments.DrinkGraph
import com.example.asah.DrinkFragments.DrinkTrack

internal class DrinkTabAdapter(var context: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                DrinkTrack()
            }
            1 -> {
                DrinkGraph()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}