package com.choidh.imagesearch.data.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.choidh.imagesearch.api.NaverApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaverRepository @Inject constructor(private val naverApi: NaverApi) {

    fun getSearchResults(keyword: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NaverPagingSource(naverApi, keyword) }
        ).liveData
}