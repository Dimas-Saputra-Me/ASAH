package com.example.asah

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.asah.databinding.ActivityMenuRutinitasBinding
import kotlin.system.exitProcess

class MenuRutinitas : AppCompatActivity() {

    lateinit var binding: ActivityMenuRutinitasBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var notificationManager: NotificationManager
    lateinit var notificationBuilder: NotificationCompat.Builder

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
                    val intent = Intent(this, MainPage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
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
                    val intent = Intent(Settings.ACTION_SETTINGS)
                    startActivity(intent)
                }
                R.id.nav_bottom_profile -> {
                    val intent = Intent(this, ProfileMenu::class.java)
                    startActivity(intent)
                }
            }

            true
        }

        // NOTIFICATION
        val channelId = "asah"
        val channelName = "Asah Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        // NOTIFICATION - Create an intent for the click action
        val intent = Intent(this, MainPage::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // NOTIFICATION - Build the notification
        notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.icon_asah)
            .setContentTitle("Judul")
            .setContentText("Isi")
            .setColor(Color.BLUE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

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
            R.id.notification -> {
                // Display the notification
                val notificationId = 1
                notificationManager.notify(notificationId, notificationBuilder.build())
            }

        }

        return super.onOptionsItemSelected(item)
    }
}