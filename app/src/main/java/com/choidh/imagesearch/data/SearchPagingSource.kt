package com.choidh.imagesearch.data

import android.util.Log
import androidx.paging.PagingSource
import com.choidh.imagesearch.api.SearchApi
import retrofit2.HttpException
import java.io.IOException

private const val SEARCH_STARTING_PAGE_INDEX = 1

class SearchPagingSource(
    private val searchApi: SearchApi,
    private val keyword: String
) : PagingSource<Int, SearchImage>() {

    val TAG: String = "로그"

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchImage> {
        val position = params.key ?: SEARCH_STARTING_PAGE_INDEX

        Log.v(TAG, "position: $position , loadSize : ${params.loadSize}")

        return try {
            val response = searchApi.searchImages(keyword, position, params.loadSize)
            val images = response.results

            LoadResult.Page(
                data = images,
                prevKey = if (position == SEARCH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (images.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}