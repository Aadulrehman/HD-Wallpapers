package com.hazel.pixabay.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favData")
data class FavouriteList (
    @PrimaryKey(autoGenerate = true)
    val favId:Int,
    val comments: Int,
    val downloads: Int,
    val hitId: Int,
    val id: Int,
    val largeImageURL: String,
    val likes: Int,
    val tags: String,
    val views: Int,
    val webformatURL: String,
)