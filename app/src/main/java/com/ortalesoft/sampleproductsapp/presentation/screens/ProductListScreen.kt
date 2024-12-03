package com.ortalesoft.sampleproductsapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ortalesoft.sampleproductsapp.R
import com.ortalesoft.sampleproductsapp.domain.model.Product
import com.ortalesoft.sampleproductsapp.presentation.ui.CenteredCircularProgressIndicator
import com.ortalesoft.sampleproductsapp.presentation.ui.LocalImageSize
import com.ortalesoft.sampleproductsapp.presentation.ui.screenBackground
import com.ortalesoft.sampleproductsapp.presentation.ui.spacing
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.ProductListState
import com.ortalesoft.sampleproductsapp.presentation.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val state by viewModel.productListState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.screenBackground.color
    ) {
        when (state) {
            is ProductListState.Loading -> CenteredCircularProgressIndicator()
            is ProductListState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium)
                ) {
                    items((state as ProductListState.Success).products) { product ->
                        ProductListItem(product) {
                            navController.navigate("product-details/${product.asin}")
                        }
                    }
                }
            }
            is ProductListState.Error -> Text(text = (state as ProductListState.Error).message)
        }
    }
}

@Composable
fun ProductListItem(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = MaterialTheme.spacing.small, horizontal = MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.productPhoto,
            contentDescription = null,
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier.size(LocalImageSize.current.small)
        )
        Column(
            modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
        ) {
            Text(
                text = product.productTitle,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = product.productPrice,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

