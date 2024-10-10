package fh.lpa.flashcards_advanced

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val _wordpairsAppDatabase : WordpairsAppDatabase by inject()
    private val _vocabularyRepository : VocabularyRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch(Dispatchers.IO) {
            //val wordpairDao = _wordpairsAppDatabase.wordpairDAO()
            //val wordpairEntityList = wordpairDao.readAll().value
            val wordpairEntityList = _vocabularyRepository.readAll().value
            if (wordpairEntityList == null || wordpairEntityList.isEmpty()) {
                // Import the CSV data if the database is empty
                //importCSVToDatabase(this@MainActivity, _wordpairsAppDatabase)
                _vocabularyRepository.importCSVToDatabase(this@MainActivity, _wordpairsAppDatabase)
            }

            // Log information
            Log.i("MAIN", "Database setup and import (if needed) completed")
        }

        Log.i("MAIN","MainActivity was created")
    }
}