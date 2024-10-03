package fh.lpa.flashcards_advanced.vocabDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import fh.lpa.flashcards_advanced.Wordpair

class DetailViewModel (private val _savedStateHandle: SavedStateHandle) : ViewModel() {
    fun read(): Wordpair {
        return _savedStateHandle["wordpair"]!!
    }
}