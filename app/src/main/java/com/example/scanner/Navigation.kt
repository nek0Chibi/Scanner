package com.example.scanner

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scanner.screens.GalleryScreen
import com.example.scanner.screens.MainScreen
import com.example.scanner.screens.ScannedTextScreen
import com.example.scanner.screens.WelcomeScreen
import com.example.scanner.screens.camera_composables.CameraScreen
import com.example.scanner.screens.camera_composables.ImagePreviewScreen

@Composable
fun Scanner(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val uiState = viewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOMESCREEN,
    ) {
        composable(route = Routes.WELCOMESCREEN) {
            WelcomeScreen(navController)
        }
        composable(route = Routes.MAINSCREEN) {
            MainScreen(navController)
        }
        composable(route = Routes.CAMERASCREEN) {
            CameraScreen(
                onCaptureImage = { viewModel.onEvent(MainUiEvent.AddNewBitmap(it)) },
                onNavigate = { event -> onNavigationEvent(event, navController) }
            )
//            CameraScreen(navController, viewModel)
        }
        composable(route = Routes.IMAGEPREVIEWSCREEN) {
            val currentBitmap = uiState.value.currentBitmap
            ImagePreviewScreen(
                bitmap = currentBitmap,
                onScanTextClick = { viewModel.onEvent(MainUiEvent.ScannedTextChanged(it)) },
                onNavigate = { event -> onNavigationEvent(event, navController) }
            )
        }
        composable(route = Routes.GALLERYSCREEN) {
            val bitmaps = uiState.value.bitmaps
            GalleryScreen(
                bitmaps = bitmaps,
                onImagePicked = { viewModel.onEvent(MainUiEvent.AddNewBitmap(it)) },
                onNavigate = { event -> onNavigationEvent(event, navController) }
            )
        }
        composable(route = Routes.SCANNEDTEXTSCREEN) {
            val scannedText = uiState.value.scannedText
            ScannedTextScreen(
                scannedText,
                onNavigate = { event -> onNavigationEvent(event, navController) }
            )
        }
    }
}

fun onNavigationEvent(event: NavigationUiEvent, navController: NavController) {
    when (event) {
        is NavigationUiEvent.NavigateToMainScreen -> navController.navigate(Routes.MAINSCREEN)
        is NavigationUiEvent.NavigateToCameraScreen -> navController.navigate(Routes.CAMERASCREEN)
        is NavigationUiEvent.NavigateToImagePreviewScreen -> navController.navigate(Routes.IMAGEPREVIEWSCREEN)
        is NavigationUiEvent.NavigateToGalleryScreen -> navController.navigate(Routes.GALLERYSCREEN)
        is NavigationUiEvent.NavigateToScannedTextScreen -> navController.navigate(Routes.SCANNEDTEXTSCREEN)
        is NavigationUiEvent.NavigateBack -> navController.navigateUp()
    }
}

sealed class NavigationUiEvent {
    data object NavigateToMainScreen : NavigationUiEvent()
    data object NavigateToCameraScreen : NavigationUiEvent()
    data class NavigateToImagePreviewScreen(val bitmap: Bitmap) : NavigationUiEvent()
    data object NavigateToGalleryScreen : NavigationUiEvent()
    data object NavigateToScannedTextScreen : NavigationUiEvent()
    data object NavigateBack : NavigationUiEvent()
}




object Routes {
    const val WELCOMESCREEN = "welcome"
    const val MAINSCREEN = "main_screen"
    const val CAMERASCREEN = "camera_screen"
    const val IMAGEPREVIEWSCREEN = "image_preview_screen"
    const val GALLERYSCREEN = "gallery_screen"
    const val SCANNEDTEXTSCREEN = "scanned_text_screen"
}
