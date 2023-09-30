package com.hazel.pixabay.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hazel.pixabay.repository.PixabayRepository

class MainViewModelFactory(private val pixabayRepository: PixabayRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(pixabayRepository) as T
    }
}