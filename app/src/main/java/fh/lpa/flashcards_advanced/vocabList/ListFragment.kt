package fh.lpa.flashcards_advanced.vocabList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fh.lpa.flashcards_advanced.R
import fh.lpa.flashcards_advanced.Wordpair
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _refresher: SwipeRefreshLayout
    private lateinit var _searchView: SearchView

    private val _listViewModel: ListViewModel by viewModel()
    private val _adapter = WordpairsAdapter(emptyList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _recyclerView = view.findViewById(R.id.rvWordlist)
        _refresher = view.findViewById(R.id.swRefresher)
        _searchView = view.findViewById(R.id.svWords)

        Log.i("LIST FRAGMENT","List Fragment was created")

        setupList()

        _listViewModel.getWordpairFilteredBySearchTerm().observe(viewLifecycleOwner) { wordpairs ->
            updateVocabularyAfterChange(wordpairs)
        }

        Log.i("LIST FRAGMENT","setupList() was executed")

        setupSearch()
        Log.i("LIST FRAGMENT","setupSearch() was executed")

    }

    private fun setupList() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            _recyclerView.context,
            linearLayoutManager.orientation
        )
        _recyclerView.addItemDecoration(dividerItemDecoration)
        _recyclerView.layoutManager = linearLayoutManager
        _recyclerView.adapter = _adapter

        _refresher.setOnRefreshListener {
            _listViewModel.refreshTriggered()
            _refresher.isRefreshing = false
        }
    }

    private fun updateVocabularyAfterChange(newWordpairs: List<Wordpair>) {
        _adapter.updateVocabulary(newWordpairs)
    }


    private fun setupSearch() {
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                _listViewModel.onSearchTermEntered(newText ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }

}