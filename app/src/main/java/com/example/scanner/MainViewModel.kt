package com.example.scanner

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val bitmap = mutableStateOf<Bitmap?>(null)

    fun updateBitmap(newBitmap: Bitmap) {
        bitmap.value = newBitmap
    }
}