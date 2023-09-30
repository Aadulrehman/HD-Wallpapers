package com.hazel.pixabay

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.hazel.pixabay.api.PixabayService
import com.hazel.pixabay.api.RetrofitHelper
import com.hazel.pixabay.databinding.ActivityMainBinding
import com.hazel.pixabay.repository.PixabayRepository
import com.hazel.pixabay.viewmodels.MainViewModel
import com.hazel.pixabay.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        val pixabayService=RetrofitHelper.getInstance().create(PixabayService::class.java)
        val repository=PixabayRepository(pixabayService)

        mainViewModel=ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.images.observe(this, Observer {
            Log.d("MYDATA",it.hits.toString())
        })
    }
}