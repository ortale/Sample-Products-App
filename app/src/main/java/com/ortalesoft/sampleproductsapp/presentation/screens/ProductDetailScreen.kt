package com.ortalesoft.sampleproductsapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ortalesoft.sampleproductsapp.R
import com.ortalesoft.sampleproductsapp.presentation.ui.CenteredCircularProgressIndicator
import com.ortalesoft.sampleproductsapp.presentation.ui.LocalImageSize
import com.ortalesoft.sampleproductsapp.presentation.ui.screenBackground
import com.ortalesoft.sampleproductsapp.presentation.ui.spacing
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.ProductDetailState
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(productId: String, viewModel: ProductViewModel = hiltViewModel()) {
    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    val state by viewModel.productDetailState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.screenBackground.color
    ) {
        when (state) {
            is ProductDetailState.Loading -> CenteredCircularProgressIndicator()
            is ProductDetailState.Success -> {
                val product = (state as ProductDetailState.Success).product
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                ) {
                    AsyncImage(
                        model = product.productPhoto,
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(LocalImageSize.current.large)
                    )
                    Text(
                        text = product.productTitle,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                    )
                    Text(
                        text = product.productPrice,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                    )
                }
            }
            is ProductDetailState.Error -> Text(text = (state as ProductDetailState.Error).message)
        }
    }
}