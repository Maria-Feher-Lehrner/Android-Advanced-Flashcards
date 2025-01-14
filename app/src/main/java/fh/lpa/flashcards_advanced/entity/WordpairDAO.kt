package fh.lpa.flashcards_advanced.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteColumn
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fh.lpa.flashcards_advanced.Wordpair

@Dao
interface WordpairDAO {
    @Query("SELECT COUNT(*) FROM wordpairs")
    suspend fun getWordCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordpair(wordpair: WordpairEntity)

    @Update
    suspend fun updateWordpair(wordpair: WordpairEntity)

    @Delete
    fun deleteWordpair(wordpair: WordpairEntity)

    @Query("DELETE FROM wordpairs")
    suspend fun deleteAll()

    @Query("SELECT * FROM wordpairs ORDER BY frenchWord")
    fun readAll(): LiveData<List<WordpairEntity>>

    @Query("SELECT * FROM wordpairs " +
            "WHERE frenchWord LIKE :searchTerm " +
            "OR germanWord LIKE :searchTerm " +
            "ORDER BY frenchWord, germanWord")
    suspend fun searchWords(searchTerm: String): List<WordpairEntity>
}