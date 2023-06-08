package com.example.flickrgallery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import coil.load
import com.example.flickrgallery.databinding.PhotoFragmentBinding

private const val TAG = "PHOTO_FRAGMENT"

class PhotoFragment: Fragment() {

    private var _binding: PhotoFragmentBinding? = null
    private val binding: PhotoFragmentBinding
        get() = checkNotNull(_binding) {
            "Cannot access because binding is null"
        }

//    private var photoUrl: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setFragmentResultListener("requestKey") { requestKeyUrl, bundle ->
//
//            photoUrl = bundle.getString("bundleKey")
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val photoUrl = bundle.getString("bundleKey")
            binding.image.load(photoUrl)
            Log.d(TAG, "Url by photo is = $photoUrl")
        }
    }
}