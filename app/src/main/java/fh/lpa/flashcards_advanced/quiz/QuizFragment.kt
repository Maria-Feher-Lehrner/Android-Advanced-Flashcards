package fh.lpa.flashcards_advanced.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fh.lpa.flashcards_advanced.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private val _quizViewModel: QuizViewModel by viewModel()
    private lateinit var _languageToggle: ToggleButton
    private lateinit var _quizWord: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing TextView
        _quizWord = view.findViewById<TextView>(R.id.tv_checkWord)
        //initializing Buttons
        _languageToggle = view.findViewById<ToggleButton>(R.id.toggle_language)
        val nextQuizWord = view.findViewById<Button>(R.id.btn_quiz_next)
        val checkAnswerToggle = view.findViewById<ToggleButton>(R.id.toggle_checkAnswer)
        val tallyPositive = view.findViewById<ImageButton>(R.id.btn_checkPositive)
        val tallyNegative = view.findViewById<Button>(R.id.btn_checkNegative)
        val navigateBack = view.findViewById<Button>(R.id.btn_end_quiz)
        val share = view.findViewById<ImageButton>(R.id.imgbtn_share)

        // Observing the current wordPair
        _quizViewModel.currentWordpair.observe(viewLifecycleOwner) { wordpair ->
            if (wordpair != null) {
                // Displaying the correct word based on toggle state
                val wordToDisplay = if (_languageToggle.isChecked) {
                    wordpair.germanWord
                } else {
                    wordpair.frenchWord
                }
                _quizWord.text = wordToDisplay
            } else {
                _quizWord.text = getString(R.string.no_words_available)
            }
        }


        //Button functions
        navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }

        nextQuizWord.setOnClickListener{
            _quizViewModel.requestRandomWord()
        }

        checkAnswerToggle.setOnCheckedChangeListener { _, isChecked ->
            val currentWord = _quizViewModel.currentWordpair.value
            if (currentWord != null) {
                if (isChecked) {
                    // Show the opposite word
                    val oppositeWord = if (_languageToggle.isChecked) {
                        currentWord.frenchWord
                    } else {
                        currentWord.germanWord
                    }
                    _quizWord.text = oppositeWord
                } else {
                    // Restore the original word
                    val originalWord = if (_languageToggle.isChecked) {
                        currentWord.germanWord
                    } else {
                        currentWord.frenchWord
                    }
                    _quizWord.text = originalWord
                }
            }
        }

        tallyPositive.setOnClickListener {
            val currentWord = _quizViewModel.currentWordpair.value
            if (currentWord != null) {
                currentWord.level++
                _quizViewModel.updateLearningLevel(currentWord, requireContext())
            }
        }

        tallyNegative.setOnClickListener {
            val currentWord = _quizViewModel.currentWordpair.value
            if (currentWord != null) {
                currentWord.level = maxOf(0, currentWord.level - 1)
                _quizViewModel.updateLearningLevel(currentWord, requireContext())
            }
        }


    }
}