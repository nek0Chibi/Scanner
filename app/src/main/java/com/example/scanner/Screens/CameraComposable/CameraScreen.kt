package com.example.scanner.Screens.CameraComposable

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController) {

    val context = LocalContext.current
    
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if(permissionState.hasPermission) {

        CameraUI(navController)
        // Permission has been granted.
        // Render the required UI like the viewfinder, or viewmodel ko call
    } else {    
        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        navController.navigateUp()
    }
}