package com.nawa.cookit.api

import com.nawa.cookit.CategoriesResponse
import com.nawa.cookit.CategoryDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface ApiService {

    @GET("categories.php")
   suspend fun getCategories(): CategoriesResponse

}

