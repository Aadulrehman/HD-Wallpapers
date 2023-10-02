package com.hazel.pixabay.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hazel.pixabay.models.Hit

@Database(entities = [Hit::class], version = 1)
abstract class HitDatabase:RoomDatabase() {
    abstract fun hitDao():hitDao

    companion object {
        private var instance: HitDatabase? = null

        fun getInstance(context: Context): HitDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HitDatabase::class.java,
                    "hit_database"
                ).fallbackToDestructiveMigration().build()
            return instance!!
        }
    }
}