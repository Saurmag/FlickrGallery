package com.example.flickrgallery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.flickrgallery.databinding.PhotoFragmentBinding
import kotlinx.coroutines.launch

private const val TAG = "PHOTO_FRAGMENT"

class PhotoFragment: Fragment() {

    private var _binding: PhotoFragmentBinding? = null
    private val binding: PhotoFragmentBinding
        get() = checkNotNull(_binding) {
            "Cannot access because binding is null"
        }

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
            lifecycleScope.launch { updatePhoto(photoUrl!!) }
            Log.d(TAG, "Url by photo is = $photoUrl")
        }
    }

    private suspend fun updatePhoto(photoUrl: String) {
        binding.image.doOnLayout { measureView ->
            lifecycleScope.launch {
                val scaledBitmap = getScaledBitmap(
                    photoUrl,
                    measureView.width,
                    requireContext()
                )
                binding.image.load(scaledBitmap)
            }
        }
    }
}