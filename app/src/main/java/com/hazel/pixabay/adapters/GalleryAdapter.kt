package com.hazel.pixabay.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hazel.pixabay.R
import com.hazel.pixabay.databinding.GalleryLayoutBinding
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.viewmodels.MainViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class galleryAdapter(private val dataList: ArrayList<Hit>):RecyclerView.Adapter<galleryAdapter.ViewHolder>() {

    private var listener: OnItemClickListener?=null
    private var favBtnListener: galleryAdapter.FavButtonClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(hit: Hit)
    }
    interface FavButtonClickListener {
        fun onFavButtonClick(item: Hit)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    fun setOnFavClickListener(listener: galleryAdapter.FavButtonClickListener) {
        favBtnListener= listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=dataList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentItem)
        }
    }
    inner class ViewHolder(private val binding: GalleryLayoutBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hit) {

            setShimmer(item)
            binding.hit=item
            setFavButton(item)

            binding.ivFav.setOnClickListener{
                favBtnListener?.onFavButtonClick(item)
                setFavButton(item)
            }
        }
        private fun setFavButton(item:Hit){
            if(item.isFav){
                binding.ivFav.setImageResource(R.drawable.baseline_favorite_24)
            }
            else{
                binding.ivFav.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }
        private fun setShimmer(item: Hit){
            binding.shimmerView.startShimmer()
            binding.ivImage.visibility = View.GONE
            binding.shimmerView.visibility = View.VISIBLE

            Picasso.get().load(item.webformatURL).into(binding.ivImage, object : Callback {
                override fun onSuccess() {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.visibility = View.GONE
                    binding.ivImage.visibility = View.VISIBLE
                }
                override fun onError(e: Exception?) {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.visibility = View.GONE
                    binding.ivImage.visibility = View.VISIBLE
                }
            })
        }

    }

}