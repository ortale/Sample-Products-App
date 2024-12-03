package com.ortalesoft.sampleproductsapp.data.datasource

import com.ortalesoft.sampleproductsapp.data.dto.ApiResponseDto
import com.ortalesoft.sampleproductsapp.data.network.ProductApiService

class ProductDataSource(private val productApiService: ProductApiService) {
    suspend fun getAllProducts(key: String): ApiResponseDto = productApiService.getAllPhoneProducts(key)
    suspend fun getProductById(key: String, asin: String): ApiResponseDto = productApiService.getPhoneProductById(key, asin)
}