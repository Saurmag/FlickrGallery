package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flickrgallery.databinding.FlickrGalleryFragmentBinding
import com.example.flickrgallery.viewmodels.FlickrGalleryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "FlickrGalleryFragment"

class FlickrGalleryFragment : Fragment() {

    private var _binding: FlickrGalleryFragmentBinding? = null

    private val binding: FlickrGalleryFragmentBinding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null!"
        }

    private val viewModel: FlickrGalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FlickrGalleryFragmentBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collectLatest {
                    val adapter = PhotoListAdapter() {
                        val photoUrl = it.urlLarge ?: it.urlMedium
                        showPhotoFragment(photoUrl!!)
                    }
                    binding.photoGrid.adapter = adapter
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showPhotoFragment(url: String) {
        val navController = findNavController()
        navController.navigate(FlickrGalleryFragmentDirections.showPhoto(url))
    }
}