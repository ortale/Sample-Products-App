package com.ortalesoft.sampleproductsapp.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("asin") val asin: String,
    @SerializedName("product_title") val productTitle: String,
    @SerializedName("product_price") val productPrice: String,
    @SerializedName("product_photo") val productPhoto: String
)