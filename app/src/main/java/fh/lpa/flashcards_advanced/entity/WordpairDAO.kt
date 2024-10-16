package fh.lpa.flashcards_advanced.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fh.lpa.flashcards_advanced.Wordpair

@Dao
interface WordpairDAO {
    @Insert
    suspend fun insertWordpair(wordpair: WordpairEntity)

    @Update
    fun updateWordpair(wordpair: WordpairEntity)

    @Delete
    fun deleteWordpair(wordpair: WordpairEntity)

    @Query("SELECT * FROM wordpairs ORDER BY frenchWord")
    fun readAll(): LiveData<List<WordpairEntity>>

    @Query("SELECT * FROM wordpairs " +
            "WHERE frenchWord LIKE :searchTerm " +
            "OR germanWord LIKE :searchTerm " +
            "ORDER BY frenchWord, germanWord")
    suspend fun searchWords(searchTerm: String): List<WordpairEntity>
}