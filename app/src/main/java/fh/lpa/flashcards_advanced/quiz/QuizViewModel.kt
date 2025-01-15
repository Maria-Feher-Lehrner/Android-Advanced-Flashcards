package fh.lpa.flashcards_advanced.quiz

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fh.lpa.flashcards_advanced.CHANNEL_ID
import fh.lpa.flashcards_advanced.R
import fh.lpa.flashcards_advanced.Wordpair
import fh.lpa.flashcards_advanced.entity.WordpairEntity
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import fh.lpa.flashcards_advanced.NOTIFICATION_ID


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

    //Updates the learning level based on the result which tally button was clicked in QuizFragment
    fun updateLearningLevel(updatedWordpair: Wordpair, context: Context) {
        Log.d("QuizViewModel", "updateLearningLevel() was called")
        val entity = _vocabRepository.mapWordpairToEntity(updatedWordpair)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _vocabRepository.updateWordPair(entity)
                Log.d("DetailViewModel", "Wordpair updated successfully!")

                // Check if level reached 3 and show notification
                if (updatedWordpair.level == 3) {
                    // Trigger notification
                    showScoreNotification(context, updatedWordpair)
                }
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error updating wordpair", e)
            }
        }
    }



    private fun showScoreNotification(context: Context, word: Wordpair) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Congratulations!")
            .setContentText("You've reached a learning score of 3 with '${word.germanWord} - ${word.frenchWord}'!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Optional: Add a large icon
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
        builder.setLargeIcon(largeIcon)

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}