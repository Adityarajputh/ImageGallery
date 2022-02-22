package com.example.imagegallery

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.imagegallery.databinding.ActivityMainBinding
import com.example.imagegallery.fragments.*
import com.example.imagegallery.utils.Communicator
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity(), Communicator {
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<View>(R.id.actionBar) as Toolbar
        setSupportActionBar(toolbar)

        this.supportActionBar?.hide()

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, StartFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
            .addToBackStack(null)
            .commit()
    }

    override fun callBottomSheet() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, BottomSheetFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun callPopUp() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer, PopUpFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun callRecycler() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer,LoadImageFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun callViewPager() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer,ViewPagerFragment())
            .commit()
    }

    override fun savePhoto() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.appContainer,SavePhotoFragment())
            .addToBackStack(null)
            .commit()
    }


}


//callPhoto()
//binding.storeBtn.isVisible = false