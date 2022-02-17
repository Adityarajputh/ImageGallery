package com.example.imagegallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.imagegallery.R
import com.example.imagegallery.databinding.FragmentBottomSheetBinding
import com.example.imagegallery.utils.Communicator
import com.google.android.material.bottomsheet.BottomSheetBehavior


class BottomSheetFragment : Fragment() {
    private lateinit var binding : FragmentBottomSheetBinding
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(layoutInflater,container,false)
        BottomSheetBehavior.from(binding.coordinatorChild).apply {
            this.isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bottomSheetBtn.setOnClickListener {
            it.preventDoubleClick()
            val bottomFragment = ModalBottomSheet()
            bottomFragment.show(childFragmentManager,bottomFragment.TAG)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communicator = activity as Communicator
                communicator.callHome()
                (requireActivity() as AppCompatActivity)
                    .supportActionBar?.hide()
            }
        })

        binding.backIcon.setOnClickListener {
            communicator = activity as Communicator
            communicator.callHome()
        }

        binding.menuIcon.setOnClickListener {
            val bottomSheetBehavior : BottomSheetBehavior<View> = BottomSheetBehavior.from(binding.coordinatorChild)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.closeImg.setOnClickListener {
            val bottomSheetBehavior : BottomSheetBehavior<View> = BottomSheetBehavior.from(binding.coordinatorChild)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

    }

    fun View.preventDoubleClick() {
        this.isEnabled = false
        this.postDelayed( { this.isEnabled = true }, 500)
    }
}