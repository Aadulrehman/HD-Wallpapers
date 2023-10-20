package com.hazel.pixabay.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazel.pixabay.HitApplication
import com.hazel.pixabay.R
import com.hazel.pixabay.adapters.FavouriteAdapter
import com.hazel.pixabay.databinding.ActivityFavouriteBinding
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.viewmodels.MainViewModel
import com.hazel.pixabay.viewmodels.MainViewModelFactory

class FavouriteActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var binding:ActivityFavouriteBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_favourite)
        recyclerView=binding.recyclerview
        recyclerView.layoutManager= GridLayoutManager(this,2)

        val repository=(application as HitApplication).pixabayRepository
        mainViewModel= ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.getFavourites().observe(this, Observer {fav->
            val adapter = FavouriteAdapter(fav as ArrayList<FavouriteList>)
            recyclerView.adapter = adapter

            adapter.setOnFavClickListener(object : FavouriteAdapter.FavButtonClickListener{
                override fun onFavButtonClick(item: FavouriteList) {
                    Toast.makeText(this@FavouriteActivity,"Removed from Favourites",Toast.LENGTH_SHORT).show()
                    mainViewModel.setFav(item.id)
                    mainViewModel.deleteFav(item.favId)
                }
            })

            adapter.setOnItemClickListener(object : FavouriteAdapter.OnItemClickListener{
                override fun onItemClick(fav: FavouriteList) {
                    val hit= Hit(fav.hitId,0,fav.comments,fav.downloads,fav.id,0,0,0,fav.largeImageURL,fav.likes,"0",0,"0",0,fav.tags,"0","0","0",0,fav.views,true,0,fav.webformatURL,0)
                    val intent = Intent(this@FavouriteActivity, DetailsActivity::class.java)
                    intent.putExtra("HitData", hit)
                    startActivity(intent)
                }
            })
        })
    }
}