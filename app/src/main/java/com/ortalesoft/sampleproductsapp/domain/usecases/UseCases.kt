package com.ortalesoft.sampleproductsapp.domain.usecases

import com.ortalesoft.sampleproductsapp.domain.model.Product
import com.ortalesoft.sampleproductsapp.domain.repository.ProductRepository
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetProductsUseCase {
    suspend fun execute(key: String): Flow<Result<List<Product>>>
}

interface GetProductByIdUseCase {
    suspend fun execute(key: String, id: String): Flow<Result<Product>>
}

class GetProductsUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    GetProductsUseCase {
    override suspend fun execute(key: String): Flow<Result<List<Product>>> {
        return productRepository.getProducts(key)
    }
}

class GetProductByIdUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    GetProductByIdUseCase {
    override suspend fun execute(key: String, id: String): Flow<Result<Product>> {
        return productRepository.getProductById(key, id)
    }
}