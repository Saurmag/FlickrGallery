package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.flickrgallery.databinding.PhotoFragmentBinding
import com.example.flickrgallery.viewmodels.PhotoFragmentViewModel
import com.example.flickrgallery.viewmodels.PhotoFragmentViewModelFactory
import kotlinx.coroutines.launch

private const val TAG = "PHOTO_FRAGMENT"

class PhotoFragment: Fragment() {

    private var _binding: PhotoFragmentBinding? = null
    private val binding: PhotoFragmentBinding
        get() = checkNotNull(_binding) {
            "Cannot access because binding is null"
        }

    private val args: PhotoFragmentArgs by navArgs()

    private val viewModel: PhotoFragmentViewModel by viewModels {
        PhotoFragmentViewModelFactory(args.url)
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.url.collect { url ->
                    url?.let {
                        updatePhoto(it)
                    }
                }
            }
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