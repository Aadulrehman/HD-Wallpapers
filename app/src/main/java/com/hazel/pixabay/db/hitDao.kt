package com.hazel.pixabay.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hazel.pixabay.models.Hit

@Dao
interface hitDao {
    @Insert
    suspend fun addHits(hits: List<Hit>)

    @Query("SELECT * FROM hit")
    suspend fun getHits():List<Hit>

    @Query("DELETE FROM hit")
    suspend fun deleteAll()
}