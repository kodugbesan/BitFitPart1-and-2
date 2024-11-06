package com.example.bitfitpart1

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.bitfitpart1.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Variables to store the totals
    private var totalCalories = 0
    private var totalEntries = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create the notification channel
        createNotificationChannel()

        // Schedule daily notifications
        scheduleDailyNotification()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)

        // Initialize dashboard values
        updateDashboard()
    }

    // Function to create notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "daily_reminder_channel"
            val channelName = "Daily Reminder Notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.description = "Channel for daily reminders to fill in data"

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    // Function to schedule daily notification
    private fun scheduleDailyNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)

        // Use FLAG_IMMUTABLE if the PendingIntent does not need to be modified
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE // Added FLAG_IMMUTABLE for API 31 and above
        )

        // Set the time for the daily notification (e.g., 9:00 AM every day)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // Schedule the alarm to trigger every day at the set time
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    // Function to update the TextViews with current totals
    private fun updateDashboard() {
        // Get the DashboardFragment from the fragment manager
        val dashboardFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? DashboardFragment
        dashboardFragment?.updateDashboardValues(totalCalories, totalEntries)
    }

    // Function to add a new entry and update totals
    fun addEntry(calories: Int) {
        totalCalories += calories
        totalEntries += 1
        updateDashboard() // Refresh the dashboard display
    }
}



