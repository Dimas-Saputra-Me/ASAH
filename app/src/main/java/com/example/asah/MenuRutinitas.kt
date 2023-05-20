package com.example.asah

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.asah.databinding.ActivityMenuRutinitasBinding
import kotlin.system.exitProcess

class MenuRutinitas : AppCompatActivity() {

    lateinit var binding: ActivityMenuRutinitasBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuRutinitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // action bar title
        supportActionBar?.title = "Main menu"

        // Page Buttons
        binding.btnMenuMakan.setOnClickListener {
            val moveIntent = Intent(this, Eat::class.java)
            startActivity(moveIntent)
        }
        binding.btnMenuMinum.setOnClickListener {
            val moveIntent = Intent(this, Drink::class.java)
            startActivity(moveIntent)
        }
        binding.btnMenuOlahraga.setOnClickListener {
            val moveIntent = Intent(this, Exercise::class.java)
            startActivity(moveIntent)
        }
        binding.btnMenuScreen.setOnClickListener {
            val moveIntent = Intent(this, Screen::class.java)
            startActivity(moveIntent)
        }

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
        binding.bottomNavigation.menu.findItem(R.id.nav_bottom_activity).isChecked = true
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bottom_home -> {
                    val intent = Intent(this, MainPage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                R.id.nav_bottom_activity -> {
                    // THIS PAGE
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