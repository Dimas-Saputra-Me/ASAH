package com.example.asah

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.asah.databinding.ActivityMainPageBinding
import com.google.android.material.navigation.NavigationView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlin.system.exitProcess

class MainPage : AppCompatActivity() {

    lateinit var binding: ActivityMainPageBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigation Menu
        drawerLayout = binding.myDrawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // None
                }
                R.id.nav_cek_data -> {
                    val intent = Intent(this, DatabaseTemp::class.java)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    moveTaskToBack(true);
                    exitProcess(-1)
                }
            }

            true
        }


        // Bottom Bar Listener
        binding.bottomNavigation.menu.findItem(R.id.nav_bottom_home).isChecked = true
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bottom_home -> {
                    // THIS PAGE
                }
                R.id.nav_bottom_activity -> {
                    val intent = Intent(this, MenuRutinitas::class.java)
                    startActivity(intent)
                }
                R.id.nav_bottom_settings -> {
                    // TODO
                }
                R.id.nav_bottom_profile -> {
                    // TODO
                }
            }

            true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        }

        when (item.itemId) {
//            R.id.ID -> {
//
//            }
        }
        return super.onOptionsItemSelected(item)
    }

}