package com.example.flickrgallery.model

import com.example.flickrgallery.model.api.FlickrApi
import com.example.flickrgallery.model.api.GalleryItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository {

    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        flickrApi = retrofit.create()
    }

    fun flickrPagingSource() = FlickrPagingSource(flickrApi = flickrApi)

    suspend fun fetchPhotos(): List<GalleryItem> = flickrApi.fetchPhotos(1).photos.galleryItems
}