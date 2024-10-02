package fh.lpa.flashcards_advanced.vocabList

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fh.lpa.flashcards_advanced.R

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _refresher: SwipeRefreshLayout
    private lateinit var _searchView: SearchView

}