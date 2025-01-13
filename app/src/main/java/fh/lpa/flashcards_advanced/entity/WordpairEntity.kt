package fh.lpa.flashcards_advanced.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "wordpairs",
    indices = [Index(value = ["frenchWord", "germanWord"], unique = true)]
    )
class WordpairEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var frenchWord: String,
    var germanWord: String,
    var level: Int
)