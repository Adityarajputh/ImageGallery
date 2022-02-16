package com.example.imagegallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imagegallery.databinding.ActivityMainBinding
import com.example.imagegallery.fragments.BottomSheetFragment
import com.example.imagegallery.fragments.NotificationFragment
import com.example.imagegallery.fragments.PopUpFragment
import com.example.imagegallery.fragments.StartFragment
import com.example.imagegallery.utils.Communicator

class MainActivity : AppCompatActivity(), Communicator {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, StartFragment())
            .commit()
    }

    override fun callNotification() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, NotificationFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun callHome() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, StartFragment())
            .commit()
    }

    override fun callBottomSheet() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, BottomSheetFragment())
            .commit()
    }

    override fun callPopUp() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, PopUpFragment())
            .commit()
    }
}