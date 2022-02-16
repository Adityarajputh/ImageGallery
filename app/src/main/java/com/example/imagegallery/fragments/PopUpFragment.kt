package com.example.imagegallery.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.R
import com.example.imagegallery.adapters.PopUpAdapter
import com.example.imagegallery.databinding.FragmentPopUpBinding
import com.example.imagegallery.utils.Communicator
import com.example.imagegallery.utils.popUpList


class PopUpFragment : Fragment() {

    private lateinit var binding : FragmentPopUpBinding
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopUpBinding.inflate(layoutInflater,container,false)

        if (binding.editText.requestFocus()) {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        binding.parentLayout.setOnTouchListener OnTouchListener@{ view, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    binding.editText.clearFocus()
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(view.windowToken,0)
                }
                MotionEvent.ACTION_UP -> {
                    binding.editText.clearFocus()
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(view.windowToken,0)
                }
            }
            return@OnTouchListener true
        }

        binding.backIcon.setOnClickListener {
            communicator = activity as Communicator
            binding.editText.clearFocus()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken,0)
            communicator.callHome()

        }

        binding.popUpBtn.setOnClickListener {
            val popUp = showPopUp(binding.popUpBtn)
            popUp.isOutsideTouchable = true
            popUp.isFocusable = true
            popUp.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popUp.elevation = 10F


            val location = IntArray(2).apply {
                binding.popUpBtn.getLocationOnScreen(this)
            }

            val position = location[1]-(binding.popUpBtn.height*4.1)
            Log.d("position",position.toString())

            popUp.showAsDropDown(binding.popUpBtn,0,-800)
            Log.d("Is showing?",popUp.isShowing.toString())
            Log.d("location coordinate",location[0].toString())
            Log.d("location coordinate",location[1].toString())
        }
    }

    private fun showPopUp(anchor: View): PopupWindow {
        val inflater = anchor.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.pop_up_window, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.popUpRecycler)

        val layoutManager = GridLayoutManager(anchor.context, 4)
        recyclerView.layoutManager = layoutManager

        val adapter = PopUpAdapter(popUpList.popUpList)
        recyclerView.adapter = adapter


        return PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}