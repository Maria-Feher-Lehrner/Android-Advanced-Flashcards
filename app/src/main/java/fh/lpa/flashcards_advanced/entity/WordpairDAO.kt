package fh.lpa.flashcards_advanced.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
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

    //TODO: Implement @Query
}