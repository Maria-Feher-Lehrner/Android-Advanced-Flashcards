package fh.lpa.flashcards_advanced.vocabList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import fh.lpa.flashcards_advanced.R
import fh.lpa.flashcards_advanced.Wordpair

class WordPairViewHolder(val listItemWordpairRootView: View) :
    RecyclerView.ViewHolder(listItemWordpairRootView) {
    val frenchWordTextView: TextView =
        listItemWordpairRootView.findViewById(R.id.tvListItemFrenchWord)
    val germanWordTextView: TextView =
        listItemWordpairRootView.findViewById(R.id.tvListItemGermanWord)

}

class WordpairsAdapter(var wordpairs: List<Wordpair>) : RecyclerView.Adapter<WordPairViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordPairViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val listItemWordpairRootView =
            layoutInflater.inflate(R.layout.list_item_wordpair, parent, false)
        return WordPairViewHolder(listItemWordpairRootView)
    }

    override fun onBindViewHolder(holder: WordPairViewHolder, position: Int) {
        val wordpair = wordpairs[position]
        holder.frenchWordTextView.text = wordpair.frenchWord
        holder.germanWordTextView.text = wordpair.germanWord
        holder.listItemWordpairRootView.setOnClickListener {
            val navHostFragment = holder.listItemWordpairRootView.findNavController()
            navHostFragment.navigate(
                ListFragmentDirections.actionListViewFragmentToDetailFragment(
                    wordpair
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return wordpairs.size
    }

    fun updateVocabulary(wordpairs: List<Wordpair>) {
        this.wordpairs = wordpairs
        notifyDataSetChanged()
    }
}