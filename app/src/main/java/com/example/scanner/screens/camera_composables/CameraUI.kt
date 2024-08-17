package com.example.scanner.screens.camera_composables

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scanner.MainViewModel
import com.example.scanner.Routes
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraUI(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CameraPreview(controller = controller)
            IconButton(
                onClick = {
                    controller.cameraSelector =
                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(Icons.Outlined.Cameraswitch, "Switch Camera")
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(Icons.Outlined.Image, "Gallery")
                }
                IconButton(
                    onClick = {
                        takePhoto(controller) { newBitmap ->
                            viewModel.updateBitmap(newBitmap)
                        }
                       navController.navigate(Routes.IMAGEPREVIEWSCREEN)
                    }
                ) {
                    Icon(Icons.Outlined.PhotoCamera, "Capture Photo")
                }

            }
        }
    }
}

fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    val backgroundExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    controller.takePicture(
        backgroundExecutor,
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val bitmap = image.toBitmap()

                onPhotoTaken(bitmap)
//                Handler(Looper.getMainLooper()).post {
//                    onPhotoTaken(bitmap)
//                }
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Error taking photo", exception)
            }
        }
    )

}