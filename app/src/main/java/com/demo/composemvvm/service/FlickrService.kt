package com.demo.composemvvm.service

import FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("photos_public.gne?format=json&nojsoncallback=2")
    suspend fun getPhotoItems(
        @Query("tags") tags: String?
    ): FlickrResponse
}