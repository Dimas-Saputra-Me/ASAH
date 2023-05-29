package com.example.asah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.example.asah.Adapter.DrinkTabAdapter
import com.google.android.material.tabs.TabLayout

class Drink : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)

        // action bar title
        supportActionBar?.title = "Menu Minuman"

        tabLayout = findViewById(R.id.drink_tabLayout)
        viewPager = findViewById(R.id.drink_viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Track"))
        tabLayout.addTab(tabLayout.newTab().setText("Graph"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = DrinkTabAdapter(
            this,
            supportFragmentManager,
            tabLayout.tabCount
        )
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                // TODO: make fragment update on tab change
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