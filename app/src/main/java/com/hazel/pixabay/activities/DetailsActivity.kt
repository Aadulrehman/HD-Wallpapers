package com.hazel.pixabay.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hazel.pixabay.R
import com.hazel.pixabay.databinding.ActivityDetailsBinding
import com.hazel.pixabay.models.Hit
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
          Picasso.get().load(data.largeImageURL).into(binding.ivImage)
    }
}