package com.example.flickrgallery.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.graphics.scale
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.lang.Float.max
import kotlin.math.roundToInt

private const val TAG = "PHOTO_UTILS"

suspend fun getScaledBitmap(path: String, destWidth: Int, context: Context): Bitmap {

    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(path)
        .build()

    val result = (loading.execute(request) as SuccessResult).drawable
    val bitmap = (result as BitmapDrawable).bitmap

    var scaleHeight = 0

    return bitmap.apply {
        scaleHeight = height + (destWidth - width)
    }.scale(destWidth, scaleHeight)
}