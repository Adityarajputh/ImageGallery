package com.example.imagegallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.imagegallery.adapters.ViewPagerAdapter
import com.example.imagegallery.databinding.FragmentViewPagerBinding
import com.example.imagegallery.utils.Communicator
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {
    private lateinit var binding : FragmentViewPagerBinding
    private lateinit var communicator: Communicator



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentViewPagerBinding.inflate(layoutInflater)
        setUpTabs()

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.ivBackBtn.setOnClickListener {
//            communicator = activity
//        }
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communicator = activity as Communicator
                communicator.callHome()
                (requireActivity() as AppCompatActivity)
                    .supportActionBar?.hide()
            }
        })

    }

    fun setUpTabs(){
        val fragmentList = arrayListOf<Fragment>(
            ViewPagerChildOne(),
            ViewPagerChildTwo(),
            ViewPagerChildThree()
        )
        val adapter = ViewPagerAdapter(fragmentList,childFragmentManager,lifecycle)
        binding.actionBarViewPager.adapter = adapter

        TabLayoutMediator(binding.actionBarTabLayout, binding.actionBarViewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        binding.actionBarTabLayout.getTabAt(0)!!.text = "Random"
        binding.actionBarTabLayout.getTabAt(1)!!.text = "Category"
        binding.actionBarTabLayout.getTabAt(2)!!.text = "Links"

    }
}