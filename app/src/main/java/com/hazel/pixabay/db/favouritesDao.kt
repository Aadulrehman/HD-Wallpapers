package com.hazel.pixabay.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hazel.pixabay.models.FavouriteList

@Dao
interface favouritesDao {

    @Insert
    suspend fun insert(fav: FavouriteList)

    @Query("SELECT * FROM favData")
    fun getFavourites():LiveData<List<FavouriteList>>

    @Query("DELETE FROM favData WHERE favId = :favId")
    suspend fun deleteFav(favId: Int)

    @Query("SELECT * FROM favData WHERE id = :id")
    suspend fun getFavoriteByHitId(id: Int): FavouriteList?
}