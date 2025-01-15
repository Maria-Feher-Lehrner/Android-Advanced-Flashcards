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

class QuizFragment : Fragment(R.layout.fragment_quiz) {

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

        //Button functions
        navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }



    }
}