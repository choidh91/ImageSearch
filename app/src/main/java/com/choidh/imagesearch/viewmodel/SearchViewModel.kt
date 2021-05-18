package com.choidh.imagesearch.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.choidh.imagesearch.data.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel @ViewModelInject constructor(private val repository: SearchRepository) :
    ViewModel() {

    val keyword = ObservableField<String>()

    private val _currentQuery = MutableLiveData<String>()

    val currentQuery: LiveData<String>
        get() = _currentQuery

    val photos = _currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchImages(query: String) {
        _currentQuery.value = query
    }


}
