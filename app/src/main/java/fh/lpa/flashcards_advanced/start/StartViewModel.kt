package fh.lpa.flashcards_advanced.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartViewModel(private val _vocabRepository: VocabularyRepository) : ViewModel() {

    private val _germanWord = MutableLiveData<String>()
    val germanWord: LiveData<String> get () = _germanWord

    private val _frenchWord = MutableLiveData<String>()
    val frenchWord: LiveData<String> get() = _frenchWord

    fun setGermanWord(word: String) {
        _germanWord.value = word
    }

    fun setFrenchWord(word: String) {
        _frenchWord.value = word
    }

    fun saveWordpair() {
        val german = _germanWord.value ?: ""
        val french = _frenchWord.value ?: ""

        if (german.isNotBlank() && french.isNotBlank()) {
            val wordpair = WordpairEntity(germanWord = german, frenchWord = french, level = 0)
            viewModelScope.launch(Dispatchers.IO) {
                _vocabRepository.addWordPair(wordpair)
            }
        }
    }
}