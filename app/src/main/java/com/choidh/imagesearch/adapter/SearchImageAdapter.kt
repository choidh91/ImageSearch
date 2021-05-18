package com.choidh.imagesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.choidh.imagesearch.data.SearchImage
import com.choidh.imagesearch.databinding.ItemSearchImageBinding

class SearchImageAdapter : PagingDataAdapter<SearchImage, SearchImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    class ImageViewHolder(private val binding: ItemSearchImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: SearchImage) {
            binding.searchImage = image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemSearchImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<SearchImage>() {
            override fun areItemsTheSame(oldItem: SearchImage, newItem: SearchImage) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: SearchImage, newItem: SearchImage) =
                oldItem == newItem
        }
    }
}