package com.example.scanner.screens

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scanner.MainViewModel
import com.example.scanner.Routes

@Composable
fun GalleryScreen(
    bitmaps: List<Bitmap>,
    navController: NavController,
    viewModel: MainViewModel
) {

    val context = LocalContext.current
    val pickImageLauncher = pickImageFromGallery(context, navController, viewModel)

    if(bitmaps.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("There are no photos here yet")
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = {
                    pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                }
            ) {
                Text("Import from Gallery")
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                IconButton(
                    onClick = {
                        pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Image")
                }
            }
            items(bitmaps) { bitmap ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f) // Maintain a square aspect ratio
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            viewModel.updateCurrentBitmap(bitmap)
                            navController.navigate(Routes.IMAGEPREVIEWSCREEN)
                        }
                        .background(Color.Gray)
                ) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun pickImageFromGallery(
    context: Context,
    navController: NavController,
    viewModel: MainViewModel
): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {
    return rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)

            val bitmap = uriToBitmap(context, it)?.asImageBitmap()
            bitmap?.let { newBitmap ->
                viewModel.updateBitmapList(newBitmap.asAndroidBitmap())
            }
        }
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    // Obtain the content resolver from the context
    val contentResolver: ContentResolver = context.contentResolver

    // Check the API level to use the appropriate method for decoding the Bitmap
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // For Android P (API level 28) and higher, use ImageDecoder to decode the Bitmap
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        // For versions prior to Android P, use BitmapFactory to decode the Bitmap
        val bitmap = context.contentResolver.openInputStream(uri)?.use { stream ->
            Bitmap.createBitmap(BitmapFactory.decodeStream(stream))
        }
        bitmap
    }
}