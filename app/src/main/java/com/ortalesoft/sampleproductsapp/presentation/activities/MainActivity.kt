package com.ortalesoft.sampleproductsapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ortalesoft.sampleproductsapp.presentation.screens.ProductDetailScreen
import com.ortalesoft.sampleproductsapp.presentation.screens.ProductListScreen
import com.ortalesoft.sampleproductsapp.presentation.ui.theme.SampleProductAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleProductAppTheme {
                NavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavGraph(startDestination: String = "list") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("list") {
            ProductListScreen(navController = navController)
        }
        composable("product-details/{asin}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("asin")
            productId?.let { ProductDetailScreen(productId = it) }
        }
    }
}