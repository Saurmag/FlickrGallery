package com.example.flickrgallery.model.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val FLICKR_API_KEY = "2bc1b4398171fb18d6c3826e2e3cbdbc"

interface FlickrApi {
    @GET("https://www.flickr.com/services/rest/?method=flickr.interestingness.getList&" +
            "api_key=$FLICKR_API_KEY&" +
            "extras=url_s%2C+url_c%2C+url_l&" +
            "format=json&" +
            "nojsoncallback=1")
    suspend fun fetchPhotos(@Query("page") page: Int): FlickrResponse
}