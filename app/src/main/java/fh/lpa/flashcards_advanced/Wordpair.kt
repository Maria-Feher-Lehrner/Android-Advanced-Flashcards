package fh.lpa.flashcards_advanced

import java.io.Serializable

class Wordpair(
    val id: Int?,
    var frenchWord: String,
    var germanWord: String,
    var level: Int
) : Serializable {}