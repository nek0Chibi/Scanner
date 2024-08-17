package com.example.scanner.screens.camera_composables

import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.scanner.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController) {

    val context = LocalContext.current
    
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!permissionState.hasPermission && !permissionState.permissionRequested) {
            permissionState.launchPermissionRequest()
        }
    }

    if (permissionState.hasPermission) {
        CameraUI(navController)
    } else {
        LaunchedEffect(permissionState.permissionRequested) {
            if (permissionState.permissionRequested && !permissionState.hasPermission) {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }
        }
    }
}