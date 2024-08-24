package com.example.scanner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scanner.screens.GalleryScreen
import com.example.scanner.screens.MainScreen
import com.example.scanner.screens.WelcomeScreen
import com.example.scanner.screens.camera_composables.CameraScreen
import com.example.scanner.screens.camera_composables.ImagePreview
import com.example.scanner.screens.camera_composables.ImagePreviewScreen


@Composable
fun Scanner(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOMESCREEN
    ) {
        composable(route = Routes.WELCOMESCREEN) {
            WelcomeScreen(navController)
        }
        composable(route = Routes.MAINSCREEN) {
            MainScreen(navController)
        }
        composable(route = Routes.CAMERASCREEN) {
            CameraScreen(navController, viewModel)
        }
        composable(route = Routes.IMAGEPREVIEWSCREEN) {
            val bitmapState = viewModel.currentBitmap.collectAsState()
            bitmapState.value?.let { bitmap ->
                ImagePreviewScreen(bitmap, navController)
            }
        }
        composable(route = Routes.GALLERYSCREEN) {
            val bitmaps = viewModel.bitmaps.collectAsState()
            GalleryScreen(bitmaps.value, navController, viewModel)
        }
    }
}

object Routes {

    val WELCOMESCREEN = "welcome"
    val MAINSCREEN = "main_screen"
    val CAMERASCREEN = "camera_screen"
    val IMAGEPREVIEWSCREEN = "image_preview_screen"
    val GALLERYSCREEN = "gallery_screen"

}