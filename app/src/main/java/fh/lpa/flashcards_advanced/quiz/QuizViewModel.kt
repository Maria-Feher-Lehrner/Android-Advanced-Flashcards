package fh.lpa.flashcards_advanced.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fh.lpa.flashcards_advanced.Wordpair
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(private val _vocabRepository: VocabularyRepository) : ViewModel() {

    private val _currentWordpair = MutableLiveData<Wordpair?>()
    val currentWordpair: LiveData<Wordpair?> get() = _currentWordpair

    private val _wordpairEntities: LiveData<List<WordpairEntity>> = _vocabRepository.readAll()

    init {
        // Load the wordpair list from the repository
        _wordpairEntities.observeForever { entityList ->
            if (entityList.isEmpty()) {
                _currentWordpair.postValue(null)
            }
        }
    }

    //Public function called by the fragment to request a random word.
    fun requestRandomWord() {
        val entityList = _wordpairEntities.value
        if (entityList.isNullOrEmpty()) {
            _currentWordpair.postValue(null) // No wordpairs available
        } else {
            provideRandomizedWord(entityList)
        }
    }

    //Private function to select a random wordpair and update LiveData.
    private fun provideRandomizedWord(entityList: List<WordpairEntity>) {
        Log.d("QuizViewModel", "provideRandomizedWord() was called")
        val randomWordpairEntity = entityList.random()
        _currentWordpair.postValue(_vocabRepository.mapEntityToWordpair(randomWordpairEntity))
    }

    fun updateLearningLevel(updatedWordpair: Wordpair) {
        Log.d("QuizViewModel", "updateLearningLevel() was called")
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