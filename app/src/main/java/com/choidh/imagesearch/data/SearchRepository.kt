package com.choidh.imagesearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig

import androidx.paging.liveData
import com.choidh.imagesearch.api.SearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val searchApi: SearchApi) {

    fun getSearchResults(keyword: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(searchApi, keyword) }
        ).liveData
}