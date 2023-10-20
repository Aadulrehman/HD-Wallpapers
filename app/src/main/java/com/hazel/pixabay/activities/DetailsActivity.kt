package com.hazel.pixabay.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hazel.pixabay.HitApplication
import com.hazel.pixabay.R
import com.hazel.pixabay.databinding.ActivityDetailsBinding
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.viewmodels.MainViewModel
import com.hazel.pixabay.viewmodels.MainViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var binding:ActivityDetailsBinding
    private lateinit var data:Hit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_details)
        val repository=(application as HitApplication).pixabayRepository
        mainViewModel= ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        setData()
        binding.ibFav.setOnClickListener{
            updateFav()
        }

    }
    private fun setData(){
          data = intent.getSerializableExtra(resources.getString(R.string.dataTag)) as Hit
          binding.hit=data
          setShimmer()
          setFavButton()
    }
    private fun setShimmer(){
        binding.ivImage.visibility = View.GONE
        binding.animatedProgressImage.visibility = View.VISIBLE

        Picasso.get().load(data.largeImageURL).into(binding.ivImage, object : Callback {
            override fun onSuccess() {
                binding.animatedProgressImage.visibility = View.GONE
                binding.ivImage.visibility = View.VISIBLE
            }
            override fun onError(e: Exception?) {
                binding.animatedProgressImage.visibility = View.GONE
                binding.ivImage.visibility = View.VISIBLE
            }
        })
    }
    private fun setFavButton(){
        if(data.isFav){
            binding.ibFav.setImageResource(R.drawable.baseline_favorite_24)
        }
        else{
            binding.ibFav.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }
    private fun updateFav(){
        if(data.isFav){
            data.isFav=false
            mainViewModel.insertFav(data)
            mainViewModel.setFav(data.id)
            binding.ibFav.setImageResource(R.drawable.baseline_favorite_border_24)
            Toast.makeText(this@DetailsActivity,resources.getString(R.string.deleteMessage),Toast.LENGTH_SHORT).show()
        }
        else{
            data.isFav=true
            mainViewModel.insertFav(data)
            mainViewModel.setFav(data.id)
            binding.ibFav.setImageResource(R.drawable.baseline_favorite_24)
            Toast.makeText(this@DetailsActivity,resources.getString(R.string.addMessage),Toast.LENGTH_SHORT).show()
        }
    }
}