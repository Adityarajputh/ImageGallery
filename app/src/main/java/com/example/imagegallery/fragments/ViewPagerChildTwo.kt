package com.example.imagegallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imagegallery.databinding.ChildFragmentTwoBinding

class ViewPagerChildTwo : Fragment() {

    private lateinit var binding : ChildFragmentTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChildFragmentTwoBinding.inflate(layoutInflater)
        return binding.root
    }

}
