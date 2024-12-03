package com.ortalesoft.sampleproductsapp.data.network

import com.ortalesoft.sampleproductsapp.data.dto.ApiResponseDto
import com.ortalesoft.sampleproductsapp.data.dto.DataDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductApiService {
    @Headers("x-rapidapi-host: real-time-amazon-data.p.rapidapi.com")
    @GET("/search")
    suspend fun getAllPhoneProducts(
        @Header("x-rapidapi-key") key: String,
        @Query("query") query: String = "Phone",
        @Query("page") page: Int = 1
    ): ApiResponseDto

    @Headers("x-rapidapi-host: real-time-amazon-data.p.rapidapi.com")
    @GET("/product-details")
    suspend fun getPhoneProductById(
        @Header("x-rapidapi-key") key: String,
        @Query("asin") asin: String
    ): ApiResponseDto
}