package com.choidh.imagesearch.api

import com.choidh.imagesearch.data.gallery.NaverPhoto
import com.google.gson.annotations.SerializedName

data class NaverResponse(
    @SerializedName("items") val results: List<NaverPhoto>
)
