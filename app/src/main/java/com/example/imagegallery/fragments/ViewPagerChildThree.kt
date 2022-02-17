package com.example.imagegallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imagegallery.databinding.ChildFragmentThreeBinding

class ViewPagerChildThree : Fragment() {

    private lateinit var binding : ChildFragmentThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChildFragmentThreeBinding.inflate(layoutInflater)
        return binding.root
    }
}
