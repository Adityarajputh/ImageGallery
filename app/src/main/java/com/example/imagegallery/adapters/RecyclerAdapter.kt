package com.example.imagegallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegallery.data.RecyclerItem
import com.example.imagegallery.databinding.RecyclerItemBinding
import com.example.imagegallery.utils.recyclerItemList


class RecyclerAdapter(val context : Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){



    inner class ViewHolder(val itemBinding: RecyclerItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(items : RecyclerItem){
            itemBinding.recyclerText.text = items.title
            itemBinding.recyclerText2.text = items.description
            Glide.with(context)
                .asGif()
                .load(items.image)
                .thumbnail(0.1f)
                .into(itemBinding.recyclerImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),
                            parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = recyclerItemList.recyclerList[position]
        holder.bindItem(items)
    }

    override fun getItemCount(): Int {
        return  recyclerItemList.recyclerList.size
    }
}