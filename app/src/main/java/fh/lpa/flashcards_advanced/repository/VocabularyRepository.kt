package fh.lpa.flashcards_advanced.repository

import androidx.lifecycle.LiveData
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import io.ktor.client.HttpClient

class VocabularyRepository (
    wordpairsAppDatabase: WordpairsAppDatabase,
    //private val _httpClient: HttpClient
) {
    private val _wordpairDAO = wordpairsAppDatabase.wordpairDAO()

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