package com.example.asah

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import com.example.asah.Database.Profile
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivityMainPageBinding
import kotlin.system.exitProcess

class MainPage : AppCompatActivity() {

    lateinit var binding: ActivityMainPageBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var notificationManager: NotificationManager
    lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inisialisasi Database
        val db = Room.databaseBuilder(applicationContext, asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()
        val profile: List<Profile> = db.ProfileDAO().getProfile()

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

        // Set Profile Name
        binding.nama.text = SpannableStringBuilder()
            .append("Halo ")
            .append(profile[0].nama, ForegroundColorSpan(Color.GREEN), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .append("!", ForegroundColorSpan(Color.BLACK), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

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
                    val intent = Intent(this, ProfileMenu::class.java)
                    startActivity(intent)
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
            R.id.notification -> {
                // Display the notification
                val notificationId = 1
                notificationManager.notify(notificationId, notificationBuilder.build())
            }

        }

        return super.onOptionsItemSelected(item)
    }

}