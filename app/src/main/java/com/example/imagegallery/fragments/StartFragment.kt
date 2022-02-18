package com.example.imagegallery.fragments


import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch

import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.imagegallery.MainActivity
import com.example.imagegallery.R
import com.example.imagegallery.databinding.FragmentStartBinding
import com.example.imagegallery.databinding.NavigationDrawerHeaderBinding
import com.example.imagegallery.utils.Communicator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException


class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private var imageUri: List<Uri>? = null
    private var position = 0
    private lateinit var getResult: ActivityResultLauncher<String>


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentStartBinding.inflate(layoutInflater)

        getResult =
            registerForActivityResult(
                ActivityResultContracts.GetMultipleContents(),
                ActivityResultCallback {
                    imageUri = it

                    Log.d("uri's", imageUri.toString())

                    val count: Int? = imageUri?.size
                    if (count != null) {
                        if (count > 1) {
                            for (i in 0 until count - 1) {
                                Picasso.get().load(imageUri!![i])
                                    .fit()
                                    .centerCrop()
                                    .into(binding.imageDisplay)
                                position = 0
                            }
                        } else {
                            Picasso.get().load(imageUri!![0])
                                .centerCrop()
                                .fit()
                                .into(binding.imageDisplay)
                            position = 0
                        }
                    }
                    binding.text.isVisible = true
                    binding.previousBtn.isVisible = true
                    binding.nextBtn.isVisible = true
                })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
//        binding.imgSw.setFactory { ImageView(requireActivity().applicationContext) }

        binding.triggerBtn.setOnClickListener {
            getResult.launch("image/*")
        }

        binding.videoTrigger.setOnClickListener {

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Picasso.get().load(R.drawable.photo3).into(mbinding.headerLogo)


//        Log.d("Path",uri.toString())


//        binding.triggerBtn.setOnClickListener {
//            requestPermission()
//        }



//        Picasso.get().load(imageUrl).into(binding.imageDisplay)

        Glide.with(requireActivity())
            .asGif()
            .load(R.raw.itachi)
            .into(binding.imageDisplay)

        binding.nextBtn.setOnClickListener {

            if (position < imageUri!!.size - 1) {
                position++
                binding.progressBar.isVisible = true
                Picasso.get().load(imageUri!![position])
                    .fit()
                    .centerCrop()
                    .into(binding.imageDisplay, object : Callback {
                        override fun onSuccess() {
                            binding.progressBar.isVisible = false
                        }

                        override fun onError(e: Exception?) {
                            TODO("Not yet implemented")
                        }
                    })
            }
        }

        binding.previousBtn.setOnClickListener {
            if (position > 0) {
                position--
                Picasso.get().load(imageUri!![position])
                    .fit()
                    .centerCrop()
                    .into(binding.imageDisplay, object : Callback {
                        override fun onSuccess() {
                            binding.progressBar.isVisible = false
                        }

                        override fun onError(e: Exception?) {
                            TODO("Not yet implemented")
                        }
                    })
            }
        }

        binding.navBtn.setOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navView.setNavigationItemSelectedListener {
            val communicator = activity as Communicator
            when (it.itemId) {
                R.id.notificationItem -> communicator.callNotification()
                R.id.bottomSheetItem -> communicator.callBottomSheet()
                R.id.popUpItem -> communicator.callPopUp()
                R.id.viewPager -> communicator.callViewPager()
                R.id.RecyclerView -> communicator.callRecycler()
                R.id.SavePhoto -> communicator.savePhoto()
            }
            true
        }
    }







}


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == pickImage) {
//            imageUri = data?.data
//            binding.imageDisplay.setImageURI(imageUri)
//        }
//    }




//fun getCameraPermission() {
//    val path = context?.filesDir?.absolutePath
//    Log.d("path",path.toString())
////
////        val file = File("$path/Images")
////        Log.d("file", file.toString())
//
//    val file = kotlin.io.path.createTempFile()
//
//
//
//    if (ActivityCompat.checkSelfPermission(requireActivity(),
//            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//    ) {
//        val uri = FileProvider.getUriForFile(requireActivity(),
//            "com.example.imagegallery.fileprovider",createImageFile())
//        Log.d("uri", uri.toString())
//        storeVideo.launch(uri)
//
//    } else {
//        getCameraPermission.launch(Manifest.permission.CAMERA)
//    }
//}


//@Throws(IOException::class)
//private fun createImageFile(): File {
//    // Create an image file name
//
//    val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//    return File.createTempFile(
//        "JPEG_TEMP_", /* prefix */
//        ".jpg", /* suffix */
//        storageDir /* directory */
//    ).apply {
//        // Save a file: path for use with ACTION_VIEW intents
//        capturedPhotoPath = absolutePath
//    }
//}