package com.hazel.pixabay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hazel.pixabay.R
import com.hazel.pixabay.databinding.GalleryLayoutBinding
import com.hazel.pixabay.models.FavouriteList
import com.hazel.pixabay.models.Hit
import com.hazel.pixabay.viewmodels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class galleryAdapter(private val dataList: ArrayList<Hit>, private val viewModel:MainViewModel):RecyclerView.Adapter<galleryAdapter.ViewHolder>() {

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
        holder.bind(currentItem,position)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentItem)
        }

    }
    inner class ViewHolder(private val binding: GalleryLayoutBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hit, position: Int) {

            CoroutineScope(Dispatchers.IO).launch {
                val isFav=viewModel.findFavById(item.id)
                withContext(Dispatchers.Main){
                    if(isFav!=null){
                        binding.ivFav.setImageResource(R.drawable.baseline_favorite_24)
                    }
                    else{
                        binding.ivFav.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
            }

            Picasso.get()
                .load(item.webformatURL)
                .into(binding.ivImage)
            binding.tvLikes.text= item.likes.toString()
            binding.tvComments.text=item.comments.toString()

            binding.ivFav.setOnClickListener{
                favBtnListener?.onFavButtonClick(item)
                CoroutineScope(Dispatchers.IO).launch {
                    val isFav=viewModel.findFavById(item.id)
                    withContext(Dispatchers.Main){
                        if(isFav!=null){
                            binding.ivFav.setImageResource(R.drawable.baseline_favorite_24)
                        }
                        else{
                            binding.ivFav.setImageResource(R.drawable.baseline_favorite_border_24)
                        }
                    }
                }
                notifyItemChanged(position)
            }
        }
    }

}