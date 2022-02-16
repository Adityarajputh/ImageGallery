package com.example.imagegallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.data.PopUpItem
import com.example.imagegallery.databinding.PopUpItemBinding

class PopUpAdapter(val popUpList : ArrayList<PopUpItem>) :  RecyclerView.Adapter<PopUpAdapter.ViewHolder>(){

    inner class ViewHolder(val popUpItemBinding: PopUpItemBinding) : RecyclerView.ViewHolder(popUpItemBinding.root){


        fun bindItem(item: PopUpItem){
            popUpItemBinding.image.setImageResource(item.icon)
            popUpItemBinding.descriptionText.text = item.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PopUpItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: PopUpAdapter.ViewHolder, position: Int) {
        val popUpItem = popUpList[position]
        holder.bindItem(popUpItem)
    }

    override fun getItemCount(): Int {
        return popUpList.size
    }
}