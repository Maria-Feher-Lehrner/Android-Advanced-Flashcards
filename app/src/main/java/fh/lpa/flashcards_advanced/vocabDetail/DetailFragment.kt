package fh.lpa.flashcards_advanced.vocabDetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import fh.lpa.flashcards_advanced.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val _detailViewModel: DetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("DETAIL FRAGMENT","was created")

        val wordpair = _detailViewModel.read()
        val etFrenchWord = view.findViewById<TextInputEditText>(R.id.etFrenchWord)
        etFrenchWord.setText(wordpair.frenchWord)
        val etGermanWord = view.findViewById<TextInputEditText>(R.id.etGermanWord)
        etGermanWord.setText((wordpair.germanWord))
        val etLearningLevel = view.findViewById<TextInputEditText>(R.id.etLearningLevel)
        etLearningLevel.setText(wordpair.level.toString())

        etFrenchWord.doAfterTextChanged { newTerm ->

        }

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}