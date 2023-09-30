package com.hazel.pixabay.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hazel.pixabay.api.PixabayService
import com.hazel.pixabay.models.PixabayList

class PixabayRepository(private val pixabayService: PixabayService) {
    private val imagesLiveData= MutableLiveData<PixabayList>()

    val images: LiveData<PixabayList>
    get()=imagesLiveData

    suspend fun getImages(apiKey:String, page:Int){
        val result=pixabayService.getImages(apiKey,page)
        if(result?.body()!=null){
            imagesLiveData.postValue(result.body())

        }
    }

}