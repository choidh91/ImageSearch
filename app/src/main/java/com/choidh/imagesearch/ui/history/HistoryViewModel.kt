package com.choidh.imagesearch.ui.history

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.choidh.imagesearch.data.history.History
import com.choidh.imagesearch.data.history.HistoryDao
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class HistoryViewModel @ViewModelInject constructor(
    private val historyDao: HistoryDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData(SEARCH_QUERY, "")

    private val historyFlow = searchQuery.asFlow().flatMapLatest { query ->
        historyDao.getHistories(query)
    }

    val histories = historyFlow.asLiveData()

    fun insertHistory(query: String) = viewModelScope.launch {
        historyDao.insert(History(keyword = query))
    }

    fun onDeleteClick(query: String) = viewModelScope.launch {
        historyDao.delete(query)
    }

    companion object {
        private const val SEARCH_QUERY = "search_query"
    }

}