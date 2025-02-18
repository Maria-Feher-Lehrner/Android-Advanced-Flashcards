package fh.lpa.flashcards_advanced.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.opencsv.CSVReader
import fh.lpa.flashcards_advanced.Wordpair
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class VocabularyRepository (
    wordpairsAppDatabase: WordpairsAppDatabase
    //private val _httpClient: HttpClient
) {
    private val _wordpairDAO = wordpairsAppDatabase.wordpairDAO()

    suspend fun initializeDatabase(context: Context, database: WordpairsAppDatabase) {
        withContext(Dispatchers.IO) {
            // Check if the database is empty
            val wordCount = database.wordpairDAO().getWordCount()
            if (wordCount > 0) {
                // Database already has data; skip import
                return@withContext
            }

            //refers to an initial CSV file with basic data (initial wordpair list)
            val inputStream = context.assets.open("wordpairs.csv")
            val reader = CSVReader(InputStreamReader(inputStream))

            var nextLine: Array<String>?

            // Skip the header row
            reader.readNext()

            while (reader.readNext().also { nextLine = it } != null) {
                val frenchWord = nextLine!![0]
                val germanWord = nextLine!![1]
                val level = nextLine!![2].toInt()

                // Insert data into the Room database from initial CSV file
                // This initial wordpair data will alway be present - even after calling the deleteAllWordPairs() function
                // because the data gets instantly replaced.
                val wordpair = WordpairEntity(frenchWord = frenchWord, germanWord = germanWord, level = level)
                database.wordpairDAO().insertWordpair(wordpair)
            }
        }
    }

    fun readAll(): LiveData<List<WordpairEntity>>{
        return _wordpairDAO.readAll()
    }

    suspend fun deleteAllWordPairs(){
        _wordpairDAO.deleteAll()
    }

    fun deleteSpecificWordpair(wordpairEntity: WordpairEntity){
        Log.d("VocabularyRepository", "deleteSpecificWordPair() was called")
        Log.d("VocabularyRepository", "Deleting wordpair: ${wordpairEntity.toString()}")

        _wordpairDAO.deleteWordpair(wordpairEntity)
    }

    suspend fun searchBy(searchTerm: String): List<WordpairEntity> {
        return _wordpairDAO.searchWords("%$searchTerm%")
    }

    suspend fun addWordPair(wordPair: WordpairEntity){
        Log.d("VocabularyRepository", "addWordpair() called")

        // Check if the wordpair already exists (based on German or French word)
        val existingWordpair = _wordpairDAO.searchByWords(wordPair.germanWord, wordPair.frenchWord)

        if (existingWordpair.isNotEmpty()) {
            // Wordpair already exists, so we throw an exception or return
            throw IllegalArgumentException("Wordpair already exists")
        }

        try {
            _wordpairDAO.insertWordpair(wordPair)
        } catch (e: Exception) {
            Log.e("VocabularyRepository", "Error inserting wordpair", e)
            throw e
        }
    }

    suspend fun updateWordPair(updatedWordpair: WordpairEntity) {
        Log.d("VocabularyRepository", "updateWordPair() was called")
        Log.d("VocabularyRepository", "Updating wordpair: ${updatedWordpair.toString()}")

        _wordpairDAO.updateWordpair(updatedWordpair)
    }

    fun mapEntityToWordpair(entity: WordpairEntity): Wordpair {
        return Wordpair(
            id = entity.id,
            frenchWord = entity.frenchWord,
            germanWord = entity.germanWord,
            level = entity.level
        )
    }

    fun mapWordpairToEntity(wordpair: Wordpair): WordpairEntity {
        return WordpairEntity(
            id = wordpair.id,
            frenchWord = wordpair.frenchWord,
            germanWord = wordpair.germanWord,
            level = wordpair.level
        )
    }
}