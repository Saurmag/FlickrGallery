package com.example.flickrgallery.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    val id: String,
    val title: String,
    @Json(name = "url_s") val url: String,
    @Json(name = "url_l") val urlLarge: String
)