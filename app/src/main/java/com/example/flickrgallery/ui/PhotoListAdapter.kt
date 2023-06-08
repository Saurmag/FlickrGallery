package com.example.flickrgallery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.flickrgallery.databinding.FlickrGalleryFragmentBinding
import com.example.flickrgallery.databinding.ListItemGalleryBinding
import com.example.flickrgallery.model.api.GalleryItem

class PhotoListAdapter(
    private val onItemClicked: (GalleryItem) -> Unit
): PagingDataAdapter<GalleryItem, PhotoViewHolder>(PHOTO_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, onItemClicked)
    }

    companion object {
        private val PHOTO_DIFF_CALLBACK = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean  =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean =
                oldItem.id == newItem.id
        }
    }
}

class PhotoViewHolder(
    private val binding: ListItemGalleryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(galleryItem: GalleryItem, onClick: (GalleryItem) -> Unit) {
        binding.itemImageView.load(galleryItem.url)
        binding.itemImageView.setOnClickListener { onClick(galleryItem) }
    }
}