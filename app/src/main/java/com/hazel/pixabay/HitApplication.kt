package com.hazel.pixabay

import android.app.Application
import com.hazel.pixabay.api.PixabayService
import com.hazel.pixabay.api.RetrofitHelper
import com.hazel.pixabay.db.HitDatabase
import com.hazel.pixabay.repository.PixabayRepository

class HitApplication:Application() {

    lateinit var pixabayRepository: PixabayRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }
    private fun initialize(){
        val pixabayService= RetrofitHelper.getInstance().create(PixabayService::class.java)
        val database=HitDatabase.getInstance(applicationContext)
        pixabayRepository=PixabayRepository(pixabayService, database,applicationContext)
    }

}