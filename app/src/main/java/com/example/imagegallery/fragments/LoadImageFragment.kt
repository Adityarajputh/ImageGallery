package com.example.imagegallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.R
import com.example.imagegallery.adapters.RecyclerAdapter
import com.example.imagegallery.databinding.FragmentLoadImageBinding
import com.example.imagegallery.utils.Communicator
import com.example.imagegallery.utils.recyclerItemList


class LoadImageFragment : Fragment() {

    private lateinit var binding : FragmentLoadImageBinding
    private lateinit var communicator: Communicator
    private lateinit var layoutManager : RecyclerView.LayoutManager
    private lateinit var adapter : RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoadImageBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerList.layoutManager = layoutManager

        adapter = RecyclerAdapter(requireActivity())
        binding.recyclerList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backIcon.setOnClickListener {
            communicator = activity as Communicator
            communicator.callHome()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communicator = activity as Communicator
                communicator.callHome()
                (requireActivity() as AppCompatActivity)
                    .supportActionBar?.hide()
            }
        })

    }

}