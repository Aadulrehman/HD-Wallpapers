package com.hazel.pixabay.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazel.pixabay.R
import com.hazel.pixabay.api.PixabayService
import com.hazel.pixabay.api.RetrofitHelper
import com.hazel.pixabay.databinding.ActivityMainBinding
import com.hazel.pixabay.adapters.galleryAdapter
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.repository.PixabayRepository
import com.hazel.pixabay.viewmodels.MainViewModel
import com.hazel.pixabay.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding:ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        val pixabayService=RetrofitHelper.getInstance().create(PixabayService::class.java)
        val repository=PixabayRepository(pixabayService)

        mainViewModel=ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        recyclerView=binding.recyclerview
        recyclerView.layoutManager= GridLayoutManager(this,2)

        mainViewModel.images.observe(this, Observer {
            val adapter = galleryAdapter(it.hits as ArrayList<Hit>)
            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : galleryAdapter.OnItemClickListener {
                override fun onItemClick(hit: Hit) {
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtra("HitData", hit)
                    startActivity(intent)
                }
            })
           // Log.d("MYDATA",it.hits.toString())
        })

        binding.btnNext.setOnClickListener{
            mainViewModel.onNextButtonClick()
        }

    }
}