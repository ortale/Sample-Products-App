package com.ortalesoft.sampleproductsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ortalesoft.sampleproductsapp.BuildConfig
import com.ortalesoft.sampleproductsapp.domain.model.Product
import com.ortalesoft.sampleproductsapp.domain.usecases.GetProductByIdUseCase
import com.ortalesoft.sampleproductsapp.domain.usecases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _productListState = MutableStateFlow<ProductListState>(ProductListState.Loading)
    val productListState: StateFlow<ProductListState> = _productListState.asStateFlow()

    private val _productDetailState = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            getProductsUseCase.execute(BuildConfig.API_KEY).collect { result ->
                _productListState.value = when (result) {
                    is Result.Success -> ProductListState.Success(result.data)
                    is Result.Failure -> ProductListState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            getProductByIdUseCase.execute(BuildConfig.API_KEY, id).collect { result ->
                _productDetailState.value = when (result) {
                    is Result.Success -> ProductDetailState.Success(result.data)
                    is Result.Failure -> ProductDetailState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}

sealed class ProductListState {
    data object Loading : ProductListState()
    data class Success(val products: List<Product>) : ProductListState()
    data class Error(val message: String) : ProductListState()
}

sealed class ProductDetailState {
    data object Loading : ProductDetailState()
    data class Success(val product: Product) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
}