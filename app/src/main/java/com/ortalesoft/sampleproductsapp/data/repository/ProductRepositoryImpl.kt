package com.ortalesoft.sampleproductsapp.data.repository

import com.google.gson.Gson
import com.ortalesoft.sampleproductsapp.data.datasource.ProductDataSource
import com.ortalesoft.sampleproductsapp.data.dto.DataDto
import com.ortalesoft.sampleproductsapp.domain.model.Product
import com.ortalesoft.sampleproductsapp.domain.repository.ProductRepository
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(private val productDataSource: ProductDataSource) : ProductRepository {
    override fun getProducts(key: String): Flow<Result<List<Product>>> {
        return flow {
            try {
                val response = productDataSource.getAllProducts(key)
                val data = Gson().fromJson(response.data, DataDto::class.java)
                val products = data.products
                emit(Result.Success(products))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    }

    override suspend fun getProductById(key: String, id: String): Flow<Result<Product>> {
        return flow {
            try {
                val response = productDataSource.getProductById(key, id)
                val product = Gson().fromJson(response.data, Product::class.java)
                emit(Result.Success(product))
            } catch (e: Exception) {
                flow {
                    emit(Result.Failure(e))
                }
            }
        }
    }
}