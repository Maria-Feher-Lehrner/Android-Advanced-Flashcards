package fh.lpa.flashcards_advanced

import java.io.Serializable

class Wordpair(
    var frenchWord: String,
    var germanWord: String,
    var level: Int
) : Serializable {}

