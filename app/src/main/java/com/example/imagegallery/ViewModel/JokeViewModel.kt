package com.example.imagegallery.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.Repository.UserRepository
import com.example.imagegallery.data.CategoryResponse
import com.example.imagegallery.data.JokeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokeViewModel(private val repository: UserRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getJoke()
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories()
        }
    }

    val jokeData : LiveData<JokeResponse>
        get() = repository.data

    val categoryData : LiveData<CategoryResponse>
        get() = repository.categoryData

    val categoryJokeData : LiveData<JokeResponse>
        get() = repository.categoryJokeData


    fun refreshJoke(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getJoke()
        }
    }

    fun categoryJoke(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryJoke(category)
        }
    }
}