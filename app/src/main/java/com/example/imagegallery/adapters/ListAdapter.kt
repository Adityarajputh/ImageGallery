package com.example.imagegallery.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.databinding.CategoryItemBinding

class ListAdapter(private val context: Context?): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var listItems: List<String>
    private lateinit var mListener: onItemClickListener


    interface onItemClickListener {

        fun onItemClick(category: String)

    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }



    inner class ViewHolder(val itemBinding: CategoryItemBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(item: String) {
            itemBinding.customTextView.text = item
//            Log.i("size",listItems.size.toString())
        }
        init {
            itemBinding.customTextView.setOnClickListener {
                listener.onItemClick(itemBinding.customTextView.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false),mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listItems[position]
        holder.bindItem(item)
        Log.i("item", item)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setCategoryList(list: List<String>) {
        listItems = list
    }
}