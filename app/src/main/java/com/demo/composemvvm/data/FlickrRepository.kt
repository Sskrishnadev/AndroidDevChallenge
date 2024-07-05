package com.demo.composemvvm.data

import FlickrResponse
import com.demo.composemvvm.service.RetrofitInstance


class FlickrRepository {
    private val flickrService = RetrofitInstance.flickrService

    suspend fun getPhotosItems(tags: String?): FlickrResponse {
        return flickrService.getPhotoItems(tags)
    }
}