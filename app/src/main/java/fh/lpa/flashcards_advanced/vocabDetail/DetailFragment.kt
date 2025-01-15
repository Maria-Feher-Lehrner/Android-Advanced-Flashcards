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

    private lateinit var etFrenchWord: TextInputEditText
    private lateinit var etGermanWord: TextInputEditText
    private lateinit var etLearningLevel: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("DETAIL FRAGMENT","was created")

        val wordpair = _detailViewModel.read()
        etFrenchWord = view.findViewById<TextInputEditText>(R.id.etFrenchWord)
        etGermanWord = view.findViewById<TextInputEditText>(R.id.etGermanWord)
        etLearningLevel = view.findViewById<TextInputEditText>(R.id.etLearningLevel)

        val saveButton = view.findViewById<Button>(R.id.btn_saveDetail)
        val backButton = view.findViewById<Button>(R.id.btn_backDetail)
        val deleteButton = view.findViewById<Button>(R.id.btn_deleteDetail)

        etFrenchWord.setText(wordpair.frenchWord)
        etGermanWord.setText((wordpair.germanWord))
        etLearningLevel.setText(wordpair.level.toString())

        //Button functions
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        saveButton.setOnClickListener{
            Log.d("DetailFragment", "Save Button was clicked")
            val updatedFrenchWord = etFrenchWord.text.toString()
            val updatedGermanWord = etGermanWord.text.toString()
            val updatedLevel = etLearningLevel.text.toString().toIntOrNull() ?: 0

            wordpair.frenchWord = updatedFrenchWord
            wordpair.germanWord = updatedGermanWord
            wordpair.level = updatedLevel

            if (wordpair.id != null) {
                _detailViewModel.updateWordpair(wordpair)
            } else {
                Log.e("DetailFragment", "Error: Wordpair id is null, cannot update!")
            }

            _detailViewModel.updateWordpair(wordpair)

            findNavController().popBackStack()
        }

        deleteButton.setOnClickListener{
            Log.d("DetailFragment", "Delete Button was clicked")

            _detailViewModel.deleteWordpair(wordpair)
            findNavController().popBackStack()
        }
    }
}