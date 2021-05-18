package com.choidh.imagesearch

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_error)
        .into(imageView)
}
