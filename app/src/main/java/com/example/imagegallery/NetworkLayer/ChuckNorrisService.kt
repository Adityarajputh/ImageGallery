package com.example.imagegallery.NetworkLayer

import com.example.imagegallery.data.CategoryResponse
import com.example.imagegallery.data.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisService {

        @GET("random")
        suspend fun getJoke() : Response<JokeResponse>

        @GET("categories")
        suspend fun getCategories() : Response<CategoryResponse>

        @GET("random")
        suspend fun getCategoryJoke(@Query("category") categoryName : String)
                : Response<JokeResponse>
}
