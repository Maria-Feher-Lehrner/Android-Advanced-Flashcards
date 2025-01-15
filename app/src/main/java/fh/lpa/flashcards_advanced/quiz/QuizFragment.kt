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

    private val quizViewModel: QuizViewModel by viewModel()
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
        _languageToggle = view.findViewById<ToggleButton>(R.id.toggleButton)
        val nextQuizWord = view.findViewById<Button>(R.id.btn_quiz_next)
        val checkAnswer = view.findViewById<Button>(R.id.btn_check)
        val tallyPositive = view.findViewById<ImageButton>(R.id.btn_checkPositive)
        val tallyNegative = view.findViewById<Button>(R.id.btn_checkNegative)
        val navigateBack = view.findViewById<Button>(R.id.btn_end_quiz)
        val share = view.findViewById<ImageButton>(R.id.imgbtn_share)

        // Observing the current wordPair
        quizViewModel.currentWordpair.observe(viewLifecycleOwner) { wordpair ->
            if (wordpair != null) {
                // Display the correct word based on toggle state
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
            quizViewModel.requestRandomWord()
        }

        tallyPositive.setOnClickListener {
            val currentWord = quizViewModel.currentWordpair.value
            if (currentWord != null) {
                currentWord.level++
                quizViewModel.updateLearningLevel(currentWord)
            }
        }

        tallyNegative.setOnClickListener {
            val currentWord = quizViewModel.currentWordpair.value
            if (currentWord != null) {
                currentWord.level = maxOf(0, currentWord.level - 1)
                quizViewModel.updateLearningLevel(currentWord)
            }
        }


    }
}