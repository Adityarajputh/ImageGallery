package com.example.imagegallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imagegallery.databinding.ChildFragmentOneBinding


class ViewPagerChildOne : Fragment() {

    private lateinit var binding: ChildFragmentOneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChildFragmentOneBinding.inflate(layoutInflater)
        return binding.root
    }
}
