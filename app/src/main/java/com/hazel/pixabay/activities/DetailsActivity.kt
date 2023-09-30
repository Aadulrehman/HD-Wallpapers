package com.hazel.pixabay.activities

import android.database.DatabaseUtils
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
        binding.tvComments.text=data.comments.toString()
        binding.tvDownloads.text=data.downloads.toString()
        binding.tvViews.text=data.views.toString()
        binding.tvLikes.text=data.likes.toString()
        binding.tvTags.text=data.tags
        Picasso.get().load(data.largeImageURL).into(binding.ivImage)
    }
}