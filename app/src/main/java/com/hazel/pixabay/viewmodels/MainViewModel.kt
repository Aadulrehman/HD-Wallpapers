package com.hazel.pixabay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazel.pixabay.models.PixabayList
import com.hazel.pixabay.repository.PixabayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val pixabayRepository: PixabayRepository):ViewModel() {
    private var pageNo=1
    init {
        fetchImages()
    }
    fun onNextButtonClick() {
        pageNo++
        fetchImages()
    }

    private fun fetchImages(){
        viewModelScope.launch(Dispatchers.IO){
            pixabayRepository.getImages("39753212-16e407a474df1977842acb508",pageNo)
        }
    }

    val images:LiveData<PixabayList>
    get() = pixabayRepository.images

}