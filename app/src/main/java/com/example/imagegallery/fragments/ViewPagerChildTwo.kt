package com.example.imagegallery.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.NetworkLayer.ChuckNorrisService
import com.example.imagegallery.NetworkLayer.NetworkInstance
import com.example.imagegallery.Repository.UserRepository
import com.example.imagegallery.ViewModel.JokeViewModel
import com.example.imagegallery.ViewModel.JokeViewModelFactory
import com.example.imagegallery.adapters.ListAdapter
import com.example.imagegallery.databinding.ChildFragmentTwoBinding

class ViewPagerChildTwo : Fragment() {

    private lateinit var binding : ChildFragmentTwoBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: ListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChildFragmentTwoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val apiInterface = NetworkInstance.getInstance().create(ChuckNorrisService::class.java)
        val repository = UserRepository(apiInterface)

        val viewModel = ViewModelProvider(this, JokeViewModelFactory(repository))
            .get(JokeViewModel::class.java)

        layoutManager = LinearLayoutManager(activity)
        binding.categoryRecyclerView.layoutManager = layoutManager

        adapter = ListAdapter(activity)
        adapter?.setOnItemClickListener(object : ListAdapter.onItemClickListener{

            override fun onItemClick(category: String) {
                viewModel.categoryJoke(category)
                val builder = AlertDialog.Builder(activity)

                viewModel.categoryJokeData.observe(viewLifecycleOwner, Observer{

                    val message: String? = viewModel.categoryJokeData.value?.value

                    builder.setTitle("Category Clicked")
                    builder.setNegativeButton("Okay",object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    })
                    builder.setMessage(message.toString())

                    val alertDialog : AlertDialog = builder.create()
                    alertDialog.show()
                    
                })

            }
        })


        viewModel.categoryData.observe(viewLifecycleOwner, Observer {

            adapter?.setCategoryList(it)
            binding?.categoryRecyclerView?.adapter = adapter
            Log.d("response", it.size.toString())
        })

//        viewModel.categoryJokeData.observe(viewLifecycleOwner, Observer {
//
//        })

//        adapter = ListAdapter(context)
//        adapter?.makeApiCall()
    }


}
