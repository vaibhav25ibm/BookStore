package com.bookora.app.data.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val price: Int,
    val originalPrice: Int,
    val rating: Float,
    val coverUrl: String,
    val category: String,
    val description: String = ""
)

data class Category(
    val id: Int,
    val name: String
)
