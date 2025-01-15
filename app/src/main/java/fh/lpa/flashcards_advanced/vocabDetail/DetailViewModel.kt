package fh.lpa.flashcards_advanced.vocabDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fh.lpa.flashcards_advanced.Wordpair
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val _savedStateHandle: SavedStateHandle,
    private val _vocabRepository: VocabularyRepository
) : ViewModel() {


    fun read(): Wordpair {
        return _savedStateHandle["wordpair"]!!
    }

    fun updateWordpair(updatedWordpair: Wordpair) {
        Log.d("DetailViewModel", "updateWordpair() was called")
        val entity = _vocabRepository.mapWordpairToEntity(updatedWordpair)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _vocabRepository.updateWordPair(entity)
                Log.d("DetailViewModel", "Wordpair updated successfully!")
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error updating wordpair", e)
            }
        }
    }

}