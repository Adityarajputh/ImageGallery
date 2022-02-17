package com.example.imagegallery.fragments


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.imagegallery.R
import com.example.imagegallery.databinding.FragmentStartBinding
import com.example.imagegallery.databinding.NavigationDrawerHeaderBinding
import com.example.imagegallery.utils.Communicator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private var imageUri: List<Uri>? = null
    private var position = 0
    private lateinit var getResult: ActivityResultLauncher<String>
    private lateinit var getPermission: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentStartBinding.inflate(layoutInflater)

        getPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {
                if (it.toString() == true.toString()) {
                    permissionGranted()
                } else {
                    Toast.makeText(requireActivity(),
                        "Media Permission Not Granted",
                        Toast.LENGTH_SHORT).show()
                }
            })

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Picasso.get().load(R.drawable.photo3).into(mbinding.headerLogo)
        binding.triggerBtn.setOnClickListener {
            permissionGranted()
        }

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
                    .into(binding.imageDisplay,object : Callback {
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
                    .into(binding.imageDisplay,object : Callback {
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
                R.id.RecyclerView -> communicator.callRecycler()
            }
            true
        }
    }


    fun permissionGranted() {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
//                  val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            getResult.launch("image/*")

        } else {
            getPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
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

