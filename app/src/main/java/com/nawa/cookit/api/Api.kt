package com.nawa.cookit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val api = retrofit.create(ApiService::class.java)