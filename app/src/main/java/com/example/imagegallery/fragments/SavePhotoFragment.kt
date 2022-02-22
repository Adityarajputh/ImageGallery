package com.example.imagegallery.fragments

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.imagegallery.R
import com.example.imagegallery.databinding.FragmentSavePhotoBinding
import com.example.imagegallery.utils.Communicator
import kotlinx.coroutines.launch
import java.io.IOException


class SavePhotoFragment : Fragment() {

    private lateinit var binding : FragmentSavePhotoBinding
    private lateinit var getPermission: ActivityResultLauncher<Array<String>>
//    private lateinit var storePhoto: ActivityResultLauncher<Void?>
    private var currentImageUri: Uri? = null
    private var currentVideoUri: Uri? = null
    private lateinit var storeActualImage : ActivityResultLauncher<Intent>
    private lateinit var storeVideo : ActivityResultLauncher<Intent>

    private lateinit var communicator : Communicator

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentSavePhotoBinding.inflate(layoutInflater)

        getPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
        }

        requestPermission()

        storeActualImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                Toast.makeText(requireActivity(),"Image Saved Successfully",Toast.LENGTH_SHORT).show()
            }
        }

        storeVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                binding.videoPlayer.setVideoURI(currentVideoUri)
                binding.videoPlayer.setMediaController(MediaController(requireActivity()))
                binding.videoPlayer.requestFocus()
                binding.videoPlayer.start()
                Toast.makeText(requireActivity(),"Video Saved Successfully",Toast.LENGTH_SHORT).show()
            }
        }
        binding.storeBtn2.setOnClickListener {
            openCameraForVideo()
        }

        binding.storeImage.setOnClickListener {
            openCamera()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity)
            .supportActionBar?.show()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communicator = activity as Communicator
                communicator.callHome()
                (requireActivity() as AppCompatActivity)
                    .supportActionBar?.hide()
            }
        })
    }

    private fun openCameraForVideo() {
        if(isWritePermissionGranted){
            val values = ContentValues()
            values.put(MediaStore.Video.Media.TITLE, "New Picture")
            values.put(MediaStore.Video.Media.DESCRIPTION, "From your Camera")
            currentVideoUri = requireActivity().contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values
            )
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentVideoUri)
            storeVideo.launch(intent)
        }else{
            Toast.makeText(requireActivity(),"Permission Not Granted",Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        if(isWritePermissionGranted){
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
            currentImageUri = requireActivity().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri)
            storeActualImage.launch(intent)
        }else{
            Toast.makeText(requireActivity(),"Permission Not Granted",Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        val isReadPermission =  ActivityCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        val isWritePermission = ActivityCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


        val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        isReadPermissionGranted = isReadPermission
        isWritePermissionGranted = isWritePermission || minSdkLevel


        val permissionRequest = mutableListOf<String>()
        if (!isWritePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }else{

        }
        if(!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(permissionRequest.isNotEmpty()){
            getPermission.launch(permissionRequest.toTypedArray())
        }
    }
}


//binding.storeBtn2.setOnClickListener {
//    storePhoto.launch()
//}


//private fun sdkCheck() : Boolean{
//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//        return true
//    }
//    return false
//}

//private fun savePhoto(name : String, bmp : Bitmap?) : Boolean{
//
//    val imageCollection : Uri = if(sdkCheck()){
//        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
//    }else{
//        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//    }
//
//    val contentValues = ContentValues().apply {
//        put(MediaStore.Images.Media.DISPLAY_NAME,"$name.jpg")
//        put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg")
//        if(bmp != null){
//            put(MediaStore.Images.Media.WIDTH,bmp.width)
//            put(MediaStore.Images.Media.HEIGHT,bmp.height)
//        }
//    }
//    return try{
//        requireActivity().contentResolver.insert(imageCollection,contentValues)?.also{
//
//            requireActivity().contentResolver.openOutputStream(it).use { outputStream ->
//                if(bmp != null){
//
//                    if (!bmp.compress(Bitmap.CompressFormat.JPEG,95,outputStream)){
//                        throw IOException("Failed to save Bitmap")
//                    }
//                }
//            }
//        } ?: throw IOException("Failed to create media store entry")
//        true
//    }catch (e: IOException){
//        e.printStackTrace()
//        false
//    }
//
//
//}


//storePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
//
//    lifecycleScope.launch {
//        if(isWritePermissionGranted){
//            if(savePhoto("myImage",it)){
//                Toast.makeText(activity,"photo Saved Successfully", Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(activity,"Photo Not Saved Successfully", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
