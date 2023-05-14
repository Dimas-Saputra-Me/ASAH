package com.example.asah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.asah.Adapter.ExerciseTabAdapter
import com.google.android.material.tabs.TabLayout

class Exercise : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // action bar title
        supportActionBar?.title = "Menu Olahraga"

        tabLayout = findViewById(R.id.exercise_tabLayout)
        viewPager = findViewById(R.id.exercise_viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Pagi - Siang"))
        tabLayout.addTab(tabLayout.newTab().setText("Sore - Malam"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ExerciseTabAdapter(
            this,
            supportFragmentManager,
            tabLayout.tabCount
        )
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })

        // Back Button
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}