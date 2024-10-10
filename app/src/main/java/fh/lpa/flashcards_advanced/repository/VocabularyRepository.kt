package fh.lpa.flashcards_advanced.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.opencsv.CSVReader
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class VocabularyRepository (
    wordpairsAppDatabase: WordpairsAppDatabase
    //private val _httpClient: HttpClient
) {
    private val _wordpairDAO = wordpairsAppDatabase.wordpairDAO()

    suspend fun importCSVToDatabase(context: Context, database: WordpairsAppDatabase) {
        withContext(Dispatchers.IO) {
            val inputStream = context.assets.open("wordpairs.csv")
            val reader = CSVReader(InputStreamReader(inputStream))

            var nextLine: Array<String>?

            // Skip the header row
            reader.readNext()

            while (reader.readNext().also { nextLine = it } != null) {
                val frenchWord = nextLine!![0]
                val germanWord = nextLine!![1]
                val level = nextLine!![2].toInt()

                // Insert data into the Room database
                val wordpair = WordpairEntity(frenchWord = frenchWord, germanWord = germanWord, level = level)
                database.wordpairDAO().insertWordpair(wordpair)
            }
        }
    }

    fun readAll(): LiveData<List<WordpairEntity>>{
        return _wordpairDAO.readAll()
    }

    suspend fun searchBy(searchTerm: String): List<WordpairEntity> {
        return _wordpairDAO.searchWords("%$searchTerm%")
    }

    suspend fun addWordPair(){
        //TODO: implement code
    }
}