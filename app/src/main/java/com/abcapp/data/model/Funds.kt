package com.abcapp.data.model

data class Funds(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val collectedValue: Int,
    val totalValue: Int,
    val startDate: String,
    val endDate: String,
    val mainImageURL: String
)