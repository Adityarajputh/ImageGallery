package com.example.imagegallery.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.adapters.SheetAdapter
import com.example.imagegallery.databinding.ModalBottomSheetBinding
import com.example.imagegallery.utils.arrayList
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {
    val TAG = "ModalBottomSheet"
    private lateinit var binding: ModalBottomSheetBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: SheetAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = ModalBottomSheetBinding.inflate(layoutInflater, container, false)

        val bottomSheetBehavior: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(binding.bottomSheetChild)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = true


        layoutManager = GridLayoutManager(activity, 2)
        binding.bottomSheetRecycler.layoutManager = layoutManager

        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                adapter = SheetAdapter(arrayList.itemListDark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                adapter = SheetAdapter(arrayList.itemList)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }


        adapter?.setOnItemClickListener(object : SheetAdapter.onItemClickListener {
            override fun onItemClicked(description: String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }

        })
        binding.bottomSheetRecycler.adapter = adapter

        return binding.root
    }
}