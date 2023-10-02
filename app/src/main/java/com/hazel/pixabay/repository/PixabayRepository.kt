package com.hazel.pixabay.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hazel.pixabay.api.PixabayService
import com.hazel.pixabay.db.HitDatabase
import com.hazel.pixabay.models.PixabayList
import com.hazel.pixabay.utils.NetworkUtils

class PixabayRepository(private val pixabayService: PixabayService, private val hitDatabase: HitDatabase, private val applicationContext: Context) {
    private val imagesLiveData= MutableLiveData<PixabayList>()

    val images: LiveData<PixabayList>
    get()=imagesLiveData

    suspend fun getImages(apiKey:String, page:Int){
        if(NetworkUtils.isInternetAvailable(applicationContext)){
            val result=pixabayService.getImages(apiKey,page)
            if(result?.body()!=null){
                hitDatabase.hitDao().addHits(result.body()!!.hits)
                imagesLiveData.postValue(result.body())
            }
        }
        else{
            val hits=hitDatabase.hitDao().getHits()
            val pixaList=PixabayList(hits,1,1)
            imagesLiveData.postValue(pixaList)
        }
    }

}