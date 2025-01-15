package fh.lpa.flashcards_advanced

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val _wordpairsAppDatabase: WordpairsAppDatabase by inject()
    private val _vocabularyRepository: VocabularyRepository by inject()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        _vocabularyRepository.readAll().observe(this) { wordpairEntityList ->
            // Check if the list is empty and import CSV if needed
            if (wordpairEntityList.isNullOrEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    _vocabularyRepository.initializeDatabase(
                        this@MainActivity,
                        _wordpairsAppDatabase
                    )
                }
            }

            // Log information
            Log.i("MAIN", "Database setup and import (if needed) completed")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            //val wordpairDao = _wordpairsAppDatabase.wordpairDAO()
            //val wordpairEntityList = wordpairDao.readAll().value
            //val wordpairEntityList = _vocabularyRepository.readAll()
            _vocabularyRepository.initializeDatabase(this@MainActivity, _wordpairsAppDatabase)

            // Log information
            Log.i("MAIN", "Database setup and import (if needed) completed")
        }

        Log.i("MAIN", "MainActivity was created")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Word Score Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifies when a word reaches level 3"
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Log.i("MainActivity", "Notification permission granted.")
            } else {
                // Permission denied
                Log.i("MainActivity", "Notification permission denied.")
            }
        }
    }
}