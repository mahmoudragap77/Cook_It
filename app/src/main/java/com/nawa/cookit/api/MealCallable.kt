package com.nawa.cookit.api

import com.nawa.cookit.model.CategoriesResponse
import com.nawa.cookit.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

}

