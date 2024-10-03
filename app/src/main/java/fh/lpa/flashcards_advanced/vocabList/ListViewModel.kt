package fh.lpa.flashcards_advanced.vocabList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import fh.lpa.flashcards_advanced.Wordpair
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListViewModel(private val _vocabRepository: VocabularyRepository) : ViewModel() {

    private val _searchTerm = MutableLiveData("")

    fun onSearchTermEntered(searchTerm: String) {
        _searchTerm.value = searchTerm
    }

    fun refreshTriggered() {
        viewModelScope.launch(Dispatchers.IO) {
            _vocabRepository.readAll()
        }
    }

    fun getWordpairs(): LiveData<List<Wordpair>> {
        return _vocabRepository.readAll().map { entityList ->
            entityList.map { entity ->
                Wordpair(
                    frenchWord = entity.frenchWord,
                    germanWord = entity.germanWord,
                    level = entity.level
                )
            }
        }
    }

    fun getWordpairFilteredBySearchTerm() : LiveData<Deferred<List<Wordpair>>> {
        return _searchTerm.map { searchTerm ->
            viewModelScope.async(Dispatchers.IO) {
                val filteredVocabulary = _vocabRepository.searchBy(searchTerm)
                filteredVocabulary.map { entity ->
                    Wordpair(
                        frenchWord = entity.frenchWord,
                        germanWord = entity.germanWord,
                        level = entity.level
                    )
                }
            }
        }
    }
}