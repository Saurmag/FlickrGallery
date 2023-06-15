package com.example.flickrgallery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotoFragmentViewModel(url: String) : ViewModel() {

    private val _url: MutableStateFlow<String?> = MutableStateFlow(null)
    val url: StateFlow<String?>
        get() = _url.asStateFlow()

    init {
        _url.value = url
    }
}

class  PhotoFragmentViewModelFactory(
    private val url: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotoFragmentViewModel(url) as T
    }
}