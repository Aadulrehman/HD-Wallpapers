package com.hazel.pixabay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hazel.pixabay.databinding.FavouriteLayoutBinding
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.Hit
import com.squareup.picasso.Picasso

class FavouriteAdapter(private val dataList: ArrayList<FavouriteList>): RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private var favBtnListener: FavouriteAdapter.FavButtonClickListener? = null
    interface FavButtonClickListener {
        fun onFavButtonClick(item: FavouriteList)
    }
    fun setOnFavClickListener(listener: FavouriteAdapter.FavButtonClickListener) {
        favBtnListener= listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavouriteLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=dataList[position]
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: FavouriteLayoutBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavouriteList) {
            Picasso.get()
                .load(item.webformatURL)
                .into(binding.ivImage)

            binding.ivFav.setOnClickListener{
                favBtnListener?.onFavButtonClick(item)
            }
        }
    }

}