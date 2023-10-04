package com.hazel.pixabay.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hazel.pixabay.api.PixabayService
import com.hazel.pixabay.db.HitDatabase
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.PixabayList
import com.hazel.pixabay.utils.NetworkUtils

class PixabayRepository(private val pixabayService: PixabayService, private val hitDatabase: HitDatabase, private val applicationContext: Context) {
    private val imagesLiveData= MutableLiveData<PixabayList>()
    //private val favouritesLiveData= MutableLiveData<FavouriteList>()

    val images: LiveData<PixabayList>
    get()=imagesLiveData

    suspend fun getImages(apiKey:String, page:Int){
        if(NetworkUtils.isInternetAvailable(applicationContext)){
            val result=pixabayService.getImages(apiKey,page)
            if(result?.body()!=null){
                hitDatabase.hitDao().deleteAll()
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

    suspend fun insertFav(fav: FavouriteList){
        hitDatabase.favDao().insert(fav)
    }

    fun getFav():LiveData<List<FavouriteList>>{
        return hitDatabase.favDao().getFavourites()
    }
    suspend fun deleteFav(favId: Int){
        hitDatabase.favDao().deleteFav(favId)
    }
    suspend fun getFavoriteByHitId(id: Int): FavouriteList?{
        return hitDatabase.favDao().getFavoriteByHitId(id)
    }


}