package com.hazel.pixabay.api

import com.hazel.pixabay.models.PixabayList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("/api/")
    suspend fun getImages(@Query("key") apiKey: String,@Query("page") page: Int, @Query("category") category: String):Response<PixabayList>
}