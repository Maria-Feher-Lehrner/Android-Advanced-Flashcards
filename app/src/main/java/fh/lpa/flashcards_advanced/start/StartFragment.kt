package fh.lpa.flashcards_advanced.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fh.lpa.flashcards_advanced.R

class StartFragment : Fragment(R.layout.fragment_start) {

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
        _inputGer = view.findViewById(R.id.eT_language1)
        _inputFra = view.findViewById(R.id.eT_language2)

        //val saveButton = view.findViewById<Button>(R.id.btn_save)
        val quizButton = view.findViewById<Button>(R.id.btn_quiz)
        val editButton = view.findViewById<Button>(R.id.btn_edit)

        editButton.setOnClickListener{
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToListViewFragment())
        }

    }

}