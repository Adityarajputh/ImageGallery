package com.example.imagegallery.utils

import androidx.core.net.toUri
import com.example.imagegallery.R
import com.example.imagegallery.data.RecyclerItem

object recyclerItemList {
        val recyclerList = arrayListOf<RecyclerItem>(
            RecyclerItem(R.raw.avatar1, "Mark Zuckerberg","Facebook"),
            RecyclerItem(R.raw.avatar2, "Tim Cook","Marketing Head"),
            RecyclerItem(R.raw.avatar3, "Steve Jobs","Apple"),
            RecyclerItem(R.raw.avatar4, "Elon Musk","Tesla"),
            RecyclerItem(R.raw.avatar1, "Jeff Bezos","Amazon")
        )
}