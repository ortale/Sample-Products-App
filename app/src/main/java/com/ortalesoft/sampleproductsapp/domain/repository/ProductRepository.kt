package com.ortalesoft.sampleproductsapp.domain.repository

import com.ortalesoft.sampleproductsapp.domain.model.Product
import kotlinx.coroutines.flow.Flow
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.Result

interface ProductRepository {
    fun getProducts(key: String): Flow<Result<List<Product>>>
    suspend fun getProductById(key: String, id: String): Flow<Result<Product>>
}