package com.ortalesoft.sampleproductsapp.domain.repository

import com.ortalesoft.sampleproductsapp.data.datasource.ProductDataSource
import com.ortalesoft.sampleproductsapp.data.network.ProductApiService
import com.ortalesoft.sampleproductsapp.data.repository.ProductRepositoryImpl
import com.ortalesoft.sampleproductsapp.domain.usecases.GetProductByIdUseCase
import com.ortalesoft.sampleproductsapp.domain.usecases.GetProductByIdUseCaseImpl
import com.ortalesoft.sampleproductsapp.domain.usecases.GetProductsUseCase
import com.ortalesoft.sampleproductsapp.domain.usecases.GetProductsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductDataSource(productApiService: ProductApiService): ProductDataSource {
        return ProductDataSource(productApiService)
    }

    @Provides
    fun provideProductRepository(productDataSource: ProductDataSource): ProductRepository {
        return ProductRepositoryImpl(productDataSource)
    }

    @Provides
    fun provideGetProductsUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCaseImpl(productRepository)
    }

    @Provides
    fun provideGetProductByIdUseCase(productRepository: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCaseImpl(productRepository)
    }
}