package com.example.scanner

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _currentBitmap = MutableStateFlow(defaultBitmap)
    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    private val _scannedText = MutableStateFlow("")

    val uiState = combine(_currentBitmap, _bitmaps, _scannedText) { current, bitmaps, text ->
        UiState(current, bitmaps, text)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState() // or set null indicating state is undetermined at the moment
    )

    fun onEvent(event: MainUiEvent) {
        when(event) {
            is MainUiEvent.AddNewBitmap -> {
                _bitmaps.update { it + event.newBitmap }
                _currentBitmap.update { event.newBitmap }
            }
            is MainUiEvent.ScannedTextChanged -> _scannedText.update { event.scannedText }
        }
    }

    companion object {
        val defaultBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

}

data class UiState(
    val currentBitmap: Bitmap = MainViewModel.defaultBitmap,
    val bitmaps: List<Bitmap> = emptyList(),
    val scannedText: String = "",
)

sealed interface MainUiEvent {
    data class AddNewBitmap(val newBitmap: Bitmap): MainUiEvent
    data class ScannedTextChanged(val scannedText: String): MainUiEvent
}