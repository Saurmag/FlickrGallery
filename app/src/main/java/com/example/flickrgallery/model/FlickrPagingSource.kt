package com.example.flickrgallery.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadResult.Page
import com.example.flickrgallery.model.api.FlickrApi
import com.example.flickrgallery.model.api.GalleryItem
import com.example.flickrgallery.model.api.PhotoResponse
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "FLICKR_PAGING_SOURCE"

private const val FLICKR_GALLERY_FIRST_PAGE = 1

class FlickrPagingSource(
    private val flickrApi: FlickrApi
): PagingSource<Int, GalleryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {

        return try {
            val nextPageNumber = params.key ?: FLICKR_GALLERY_FIRST_PAGE
            val data = flickrApi.fetchPhotos(nextPageNumber).photos
            val nextKey = if (data.page == 5) null else data.page + 1

            Page(
                data = data.galleryItems,
                nextKey = nextKey,
                prevKey = null
            )
        }
        catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}