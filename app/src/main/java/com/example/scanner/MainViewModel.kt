package com.example.scanner

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _currentBitmap = MutableStateFlow<Bitmap?>(null)
    val currentBitmap = _currentBitmap.asStateFlow()
    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()


    fun updateBitmapList(newBitmap: Bitmap) {
        _bitmaps.value += newBitmap
        updateCurrentBitmap(newBitmap)
    }

    fun updateCurrentBitmap(newBitmap: Bitmap) {
        _currentBitmap.value = newBitmap
    }
}