package com.choidh.imagesearch.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choidh.imagesearch.data.History
import com.choidh.imagesearch.databinding.ItemHistoryBinding


class HistoryAdapter(val clickListener: (History) -> Unit, val deleteClickListener: (String) -> Unit) :
    ListAdapter<History, HistoryAdapter.HistoryItemViewHolder>(diffUtil),
    Filterable {

    private lateinit var mHistoryList: List<History>
    private lateinit var mFilterHistoryList: List<History>
    private var mFilterStr: String? = null

    inner class HistoryItemViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historyModel: History) {

            if (mFilterStr?.isNotEmpty() == true) {
                val text = historyModel.keyword
                val startPos = text?.toLowerCase()?.indexOf(mFilterStr!!.toLowerCase())
                val endPos = startPos?.plus(mFilterStr!!.length)
                if (startPos != -1) {
                    val sp = SpannableString(text)
                    val blueColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.BLUE))
                    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null)
                    if (startPos != null && endPos != null) {
                        sp.setSpan(
                            highlightSpan,
                            startPos,
                            endPos,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    binding.keywordTextView.text = sp
                } else {
                    binding.keywordTextView.text = historyModel.keyword
                }
            } else {
                binding.keywordTextView.text = historyModel.keyword
            }
            binding.deleteButton.setOnClickListener {
                deleteClickListener(historyModel.keyword.orEmpty())
            }
            binding.root.setOnClickListener { clickListener(historyModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                mFilterHistoryList = if (charString.isEmpty()) {
                    mFilterStr = ""
                    mHistoryList.toMutableList()
                } else {
                    mFilterStr = charString.toLowerCase().trim()
                    val filteredList = mHistoryList
                        .filter { it.keyword?.toLowerCase()?.contains(charString)!! }
                        .toMutableList()
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mFilterHistoryList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if(results?.values == null) {
                    return
                }
                submitList(results?.values as MutableList<History>)
                notifyDataSetChanged()
            }

        }
    }

    fun updateList(historyList: List<History>) {
        mHistoryList = historyList
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<History>() {
            override fun areContentsTheSame(oldItem: History, newItem: History) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: History, newItem: History) =
                oldItem.uid == newItem.uid
        }
    }
}