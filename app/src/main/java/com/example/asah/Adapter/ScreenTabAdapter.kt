package com.example.asah.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.asah.ExerciseFragments.PagiSiang
import com.example.asah.ExerciseFragments.SoreMalam
import com.example.asah.ScreenFragments.ScreenTime
import com.example.asah.ScreenFragments.SleepTime

internal class ScreenTabAdapter(var context: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                ScreenTime()
            }
            1 -> {
                SleepTime()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}