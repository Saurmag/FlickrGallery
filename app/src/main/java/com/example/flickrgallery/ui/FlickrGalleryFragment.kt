package com.example.flickrgallery.ui

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.FlickrGalleryFragmentBinding
import com.example.flickrgallery.model.PhotoRepository
import com.example.flickrgallery.model.api.FlickrApi
import com.example.flickrgallery.viewmodels.FlickrGalleryViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

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

        val adapter = PhotoListAdapter() {
            val photoUrl = it.urlLarge
            setFragmentResult("requestKey", bundleOf("bundleKey" to photoUrl))

            Log.d(TAG, "Url by photo is = $photoUrl")
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                replace(R.id.fragment_container, PhotoFragment())
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        binding.photoGrid.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}