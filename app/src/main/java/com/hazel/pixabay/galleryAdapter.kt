package com.hazel.pixabay

import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.hazel.pixabay.databinding.GalleryLayoutBinding
import com.hazel.pixabay.models.Hit
import com.squareup.picasso.Picasso

class galleryAdapter(private val dataList: ArrayList<Hit>):RecyclerView.Adapter<galleryAdapter.ViewHolder>() {

    private var listener:OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(hit: Hit)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
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
            Picasso.get()
                .load(item.webformatURL)
                .into(binding.ivImage)
        }
    }
}