package com.choidh.imagesearch.data

import com.google.gson.annotations.SerializedName

data class SearchImage (
    @SerializedName("thumbnail") val thumbnail: String
)