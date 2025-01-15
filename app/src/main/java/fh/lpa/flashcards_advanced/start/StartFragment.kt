package fh.lpa.flashcards_advanced.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fh.lpa.flashcards_advanced.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment(R.layout.fragment_start) {

    private val startViewModel: StartViewModel by viewModel()
    private lateinit var _inputGer: EditText
    private lateinit var _inputFra: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Variables editText fields
        _inputGer = view.findViewById(R.id.eT_language1)
        _inputFra = view.findViewById(R.id.eT_language2)

        // Hints for the EditTexts
        _inputGer.hint = "das Beispiel"
        _inputFra.hint = "le exemple"

        //Variables buttons
        val saveButton = view.findViewById<Button>(R.id.btn_save)
        val quizButton = view.findViewById<Button>(R.id.btn_quiz)
        val editButton = view.findViewById<Button>(R.id.btn_edit)
        val deleteButton = view.findViewById<Button>(R.id.btn_delete)

        // Observing LiveData and updating EditText
        /*startViewModel.germanWord.observe(viewLifecycleOwner, { german ->
            _inputGer.setText(german)
        })

        startViewModel.frenchWord.observe(viewLifecycleOwner, { french ->
            _inputFra.setText(french)
        })*/

        //Text-change listener for editTexts
        _inputGer.addTextChangedListener { text ->
            startViewModel.setGermanWord(text.toString())
        }
        _inputFra.addTextChangedListener { text ->
            startViewModel.setFrenchWord(text.toString())
        }

        //Button functions
        editButton.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToListViewFragment())
        }
        quizButton.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToQuizFragment())
        }
        saveButton.setOnClickListener {
            Log.d("StartFragment", "Save button clicked!")
            startViewModel.saveWordpair(requireContext())
            _inputGer.setText("")
            _inputFra.setText("")
        }
        deleteButton.setOnClickListener {
            Log.d("StartFragment", "Delete button clicked!")
            startViewModel.deleteAllVocabulary()
        }

    }
}