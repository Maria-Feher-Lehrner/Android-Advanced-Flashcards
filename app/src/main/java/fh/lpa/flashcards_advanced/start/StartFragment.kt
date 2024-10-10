package fh.lpa.flashcards_advanced.start

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import fh.lpa.flashcards_advanced.R

class StartFragment : Fragment(R.layout.fragment_start){

    private lateinit var _inputGer: EditText
    private lateinit var _inputFra: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _inputGer = view.findViewById(R.id.etGermanWord)
        _inputFra = view.findViewById(R.id.etFrenchWord)

    }

}