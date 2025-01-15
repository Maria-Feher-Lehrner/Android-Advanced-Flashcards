package fh.lpa.flashcards_advanced.start

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun saveWordpair(context: Context) {
        Log.d("StartViewModel", "saveWordpair() called")
        val german = _germanWord.value ?: ""
        val french = _frenchWord.value ?: ""

        if (german.isNotBlank() && french.isNotBlank()) {
            Log.d("StartViewModel", "Saving wordpair: $german - $french")
            val wordpair = WordpairEntity(germanWord = german, frenchWord = french, level = 0)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _vocabRepository.addWordPair(wordpair)
                    // Notify the user that the wordpair was successfully saved
                } catch (e: IllegalArgumentException) {
                    // This error indicates that the wordpair already exists
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Dieses Wortpaar existiert bereits!", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.e("StartViewModel", "Error saving wordpair", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error saving wordpair", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Log.d("StartViewModel", "German or French word is blank. Skipping save.")
        }
    }

    fun deleteAllVocabulary() {
        viewModelScope.launch {
            try {
                _vocabRepository.deleteAllWordPairs()
                Log.d("StartViewModel", "All word pairs deleted successfully")
            } catch (e: Exception) {
                Log.e("StartViewModel", "Error deleting word pairs", e)
            }
        }
    }
}