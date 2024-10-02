package fh.lpa.flashcards_advanced.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordpairEntity::class],
    version = 1
)

abstract class WordpairsAppDatabase : RoomDatabase() {

    abstract fun wordpairDAO(): WordpairDAO
}