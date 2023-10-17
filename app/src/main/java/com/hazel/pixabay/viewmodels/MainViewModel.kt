package com.hazel.pixabay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.models.PixabayList
import com.hazel.pixabay.repository.PixabayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val pixabayRepository: PixabayRepository):ViewModel() {
    private var pageNo=1
    private var category=""
    init {
        fetchImages()
    }
    fun onNextButtonClick() {
        pageNo++
        fetchImages()
    }
    fun insertFav(hit: Hit){
        val newFav=FavouriteList(0,hit.comments,hit.downloads,hit.hitId,hit.id,hit.largeImageURL,hit.likes,hit.tags,hit.views,hit.webformatURL)
        viewModelScope.launch (Dispatchers.IO){
            val exists=pixabayRepository.getFavoriteByHitId(hit.id)
            if(exists==null){
                pixabayRepository.insertFav(newFav)
            }
            else{
               deleteFav(pixabayRepository.getFavoriteByHitId(hit.id)!!.favId)
            }
        }
    }

    fun getFavourites():LiveData<List<FavouriteList>>{
        return pixabayRepository.getFav()
    }
    fun deleteFav(favId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            pixabayRepository.deleteFav(favId)
        }
    }
    suspend fun findFavById(hitId:Int):FavouriteList?{
        return pixabayRepository.getFavoriteByHitId(hitId)
    }

    private fun fetchImages(){
        viewModelScope.launch(Dispatchers.IO){
            pixabayRepository.getImages("39753212-16e407a474df1977842acb508",pageNo,category)
        }
    }
    fun setCategory(newCategory: String){
        category=newCategory
        fetchImages()
    }

    val images:LiveData<PixabayList>
    get() = pixabayRepository.images

}