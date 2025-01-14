package fh.lpa.flashcards_advanced.start

import android.util.Log
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
    val germanWord: LiveData<String> get() = _germanWord

    private val _frenchWord = MutableLiveData<String>()
    val frenchWord: LiveData<String> get() = _frenchWord

    fun setGermanWord(word: String) {
        _germanWord.value = word
    }

    fun setFrenchWord(word: String) {
        _frenchWord.value = word
    }

    /*fun saveWordpair() {
        Log.d("StartViewModel", "saveWordpair() called")

        val german = _germanWord.value ?: ""
        val french = _frenchWord.value ?: ""

        if (german.isNotBlank() && french.isNotBlank()) {
            val wordpair = WordpairEntity(germanWord = german, frenchWord = french, level = 0)

            Log.d("StartViewModel", "Saving wordpair: $wordpair")

            // wordpair saved asynchronously
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    Log.d("StartViewModel", "Calling repository to add wordpair")
                    // Insert into the database
                    _vocabRepository.addWordPair(wordpair)
                    Log.d("StartViewModel", "Wordpair saved successfully")

                    // After the insert completes, reset the fields
                    _germanWord.postValue("")
                    _frenchWord.postValue("")
                    Log.d("StartViewModel", "Fields reset")
                } catch (e: Exception) {
                    Log.e("StartViewModel", "Error saving wordpair", e)
                }
            }
        } else {
            Log.d("StartViewModel", "German or French word is blank. Skipping save.")
        }
    }*/

    fun saveWordpair() {
        Log.d("StartViewModel", "saveWordpair() called")
        val german = _germanWord.value ?: ""
        val french = _frenchWord.value ?: ""

        if (german.isNotBlank() && french.isNotBlank()) {
            Log.d("StartViewModel", "Saving wordpair: $german - $french")
            val wordpair = WordpairEntity(germanWord = german, frenchWord = french, level = 0)
            viewModelScope.launch(Dispatchers.IO) {
                _vocabRepository.addWordPair(wordpair)
            }
        } else {
        Log.d("StartViewModel", "German or French word is blank. Skipping save.")
    }
    }
}