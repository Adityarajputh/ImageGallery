package com.example.imagegallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imagegallery.NetworkLayer.ChuckNorrisService
import com.example.imagegallery.NetworkLayer.NetworkInstance
import com.example.imagegallery.Repository.UserRepository
import com.example.imagegallery.ViewModel.JokeViewModel
import com.example.imagegallery.ViewModel.JokeViewModelFactory
import com.example.imagegallery.data.JokeResponse
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val apiInterface = NetworkInstance.getInstance().create(ChuckNorrisService::class.java)
        val repository = UserRepository(apiInterface)

        val viewModel = ViewModelProvider(this, JokeViewModelFactory(repository))
            .get(JokeViewModel::class.java)

        viewModel.jokeData.observe(viewLifecycleOwner, Observer { condition ->
            checkProgressBar(condition)
            binding.jokeText.text = condition.value
            binding.refreshBtn.setOnClickListener {
                checkProgressBar(condition)
                viewModel.refreshJoke()
            }
        })


    }
    private fun checkProgressBar(it: JokeResponse?) {
        if(it != null){
            binding.progressBar.visibility = View.GONE
        }
    }

}
