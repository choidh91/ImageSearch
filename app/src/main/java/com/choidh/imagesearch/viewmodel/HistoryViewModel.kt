package com.choidh.imagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.choidh.imagesearch.data.History
import com.choidh.imagesearch.data.HistoryRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HistoryRepository(application)
    private val history = repository.getAll()

    fun getAll(): LiveData<List<History>> {
        return this.history
    }

    fun insert(history: History) {
        repository.insert(history)
    }

    fun delete(keyword: String) {
        repository.delete(keyword)
    }
}