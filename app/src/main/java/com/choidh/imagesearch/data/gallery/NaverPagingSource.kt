package com.choidh.imagesearch.data.gallery

import androidx.paging.PagingSource
import com.choidh.imagesearch.api.NaverApi
import retrofit2.HttpException
import java.io.IOException

private const val SEARCH_STARTING_PAGE_INDEX = 1

class NaverPagingSource(
    private val naverApi: NaverApi,
    private val keyword: String
) : PagingSource<Int, NaverPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NaverPhoto> {
        val position = params.key ?: SEARCH_STARTING_PAGE_INDEX

        return try {
            val response = naverApi.searchImages(keyword, position, params.loadSize)
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