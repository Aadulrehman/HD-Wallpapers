package com.hazel.pixabay.models

data class PixabayList(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)