package com.choidh.imagesearch.api

import com.choidh.imagesearch.data.SearchImage
import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("items") val results: List<SearchImage>
)