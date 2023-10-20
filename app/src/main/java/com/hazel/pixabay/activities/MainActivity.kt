package com.hazel.pixabay.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hazel.pixabay.HitApplication
import com.hazel.pixabay.R
import com.hazel.pixabay.databinding.ActivityMainBinding
import com.hazel.pixabay.adapters.galleryAdapter
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.viewmodels.MainViewModel
import com.hazel.pixabay.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding:ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private var selectedButton: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        val repository=(application as HitApplication).pixabayRepository
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
            adapter.setOnFavClickListener(object: galleryAdapter.FavButtonClickListener{
                override fun onFavButtonClick(hit: Hit) {
                    if(hit.isFav){
                        Toast.makeText(this@MainActivity,resources.getString(R.string.deleteMessage),Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@MainActivity,resources.getString(R.string.addMessage),Toast.LENGTH_SHORT).show()
                    }
                    mainViewModel.setFav(hit.id)
                    mainViewModel.insertFav(hit)
                }
            })
        })

        binding.btnFav.setOnClickListener {
            val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
            startActivity(intent)
            selectedButton?.setBackgroundResource(R.drawable.button_not_selected)
            binding.bottomNavigation.selectedItemId=R.id.back
            mainViewModel.resetButtonCLick()
        }
        showCategories()
        navigateBottomNavigation()
    }

    private fun showCategories(){
        val categoryArray = resources.getStringArray(R.array.category_list)
        for (i in 1..10) {
            val textBtn = TextView(this)
            textBtn.text = categoryArray[i].toUpperCase()
            textBtn.setPadding(20, 20, 20, 20)
            textBtn.setTextSize(resources.getDimension(R.dimen.textview_text_size))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(resources.getDimensionPixelSize(R.dimen.textview_spacing), 0, 0, 0)
            textBtn.setBackgroundResource(R.drawable.button_not_selected)
            textBtn.setOnClickListener {
                mainViewModel.setCategory(categoryArray[i])
                selectButton(textBtn)
                mainViewModel.resetButtonCLick()
                binding.bottomNavigation.selectedItemId=R.id.back
            }
            binding.buttonContainer.addView(textBtn,params)
        }
    }
    private fun selectButton(button: TextView) {
        selectedButton?.setBackgroundResource(R.drawable.button_not_selected)
        button.setBackgroundResource(R.drawable.button_selected)
        selectedButton = button
    }
    private fun navigateBottomNavigation(){
        binding.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.back -> mainViewModel.onBackButtonClick()
                R.id.next -> mainViewModel.onNextButtonClick()
            }
            true
        }
    }

}