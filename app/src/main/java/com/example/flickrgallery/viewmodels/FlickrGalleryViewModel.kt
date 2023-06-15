package com.example.flickrgallery.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.flickrgallery.model.PhotoRepository
import com.example.flickrgallery.model.api.GalleryItem
import kotlinx.coroutines.flow.Flow

private const val TAG = "FLICKR_GALLERY_VIEWMODEL"

class FlickrGalleryViewModel : ViewModel() {

    private val photoRepository = PhotoRepository()

    val items: Flow<PagingData<GalleryItem>> = Pager(
        config = PagingConfig(pageSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { photoRepository.flickrPagingSource() }
    ).flow
        .cachedIn(viewModelScope)

}