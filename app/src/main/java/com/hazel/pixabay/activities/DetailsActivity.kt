package com.hazel.pixabay.activities

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hazel.pixabay.R
import com.hazel.pixabay.databinding.ActivityDetailsBinding
import com.hazel.pixabay.models.Hit
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_details)

        setData()

    }
    private fun setData(){
        val data = intent.getSerializableExtra("HitData") as Hit
          binding.hit=data
          setShimmer(data)
    }
    private fun setShimmer(item: Hit){
        binding.ivImage.visibility = View.GONE
        binding.animatedProgressImage.visibility = View.VISIBLE

        Picasso.get().load(item.largeImageURL).into(binding.ivImage, object : Callback {
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
}