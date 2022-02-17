package com.example.imagegallery

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.imagegallery.databinding.ActivityMainBinding
import com.example.imagegallery.fragments.*
import com.example.imagegallery.utils.Communicator

class MainActivity : AppCompatActivity(), Communicator {
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.actionBar) as Toolbar
        setSupportActionBar(toolbar)
        this.supportActionBar?.hide()
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

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

    override fun callRecycler() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer,LoadImageFragment())
            .commit()
    }

    override fun callViewPager() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer,ViewPagerFragment())
            .commit()
    }

}