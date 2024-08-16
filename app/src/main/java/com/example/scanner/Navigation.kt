package com.example.scanner

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scanner.Screens.CameraComposable.CameraScreen
import com.example.scanner.Screens.CameraComposable.ImagePreview
import com.example.scanner.Screens.MainScreen
import com.example.scanner.Screens.WelcomeScreen


@Composable
fun Scanner(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.WELCOMESCREEN) {
        composable(route = Routes.WELCOMESCREEN) {
            WelcomeScreen(navController)
        }
        composable(route = Routes.WELCOMESCREEN) {
            MainScreen(navController)
        }
        composable(route = Routes.CAMERASCREEN) {
            CameraScreen(navController)
        }
        composable(route = Routes.IMAGEPREVIEWSCREEN) {
            viewModel.bitmap.value?.let { ImagePreview(it) }
        }
    }
}

object Routes {

    val WELCOMESCREEN = "welcome"
    val MAINSCREEN = "main_screen"
    val CAMERASCREEN = "camera_screen"
    val IMAGEPREVIEWSCREEN = "image_preview_screen"

}