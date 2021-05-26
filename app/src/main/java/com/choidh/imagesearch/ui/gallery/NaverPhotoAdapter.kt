package com.choidh.imagesearch.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.choidh.imagesearch.R
import com.choidh.imagesearch.data.gallery.NaverPhoto
import com.choidh.imagesearch.databinding.ItemNaverPhotoBinding

class NaverPhotoAdapter : PagingDataAdapter<NaverPhoto, NaverPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    inner class PhotoViewHolder(private val binding: ItemNaverPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: NaverPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.thumbnail)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemNaverPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<NaverPhoto>() {
            override fun areItemsTheSame(oldItem: NaverPhoto, newItem: NaverPhoto) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NaverPhoto, newItem: NaverPhoto) =
                oldItem == newItem
        }
    }
}