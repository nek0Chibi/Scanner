package com.example.scanner.screens.camera_composables

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scanner.MainUiEvent
import com.example.scanner.NavigationUiEvent
import com.example.scanner.Routes
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun ImagePreviewScreen(
    bitmap: Bitmap,
    onScanTextClick: (String) -> Unit,
    onNavigate: (NavigationUiEvent) -> Unit,
) {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier =
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
        )

        IconButton(
            onClick = {
                onNavigate(NavigationUiEvent.NavigateBack)
            },
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                val image = InputImage.fromBitmap(bitmap, 0)
                recognizer
                    .process(image)
                    .addOnSuccessListener {
                        onScanTextClick(it.text)
//                        viewModel.onEvent(MainUiEvent.ScannedTextChanged(it.text))
                    }.addOnFailureListener {
                        Log.e("TEXT_REC", it.message.toString())
                    }

                onNavigate(NavigationUiEvent.NavigateToScannedTextScreen)
            },
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
        ) {
            Text("Scan Image")
        }

    }
}
