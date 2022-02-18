package com.example.imagegallery.utils

interface Communicator {
    fun callNotification()
    fun callHome()
    fun callBottomSheet()
    fun callPopUp()
    fun callRecycler()
    fun callViewPager()
    fun savePhoto()
}