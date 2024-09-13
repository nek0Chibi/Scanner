package com.example.scanner.screens

import android.content.Context
import android.content.Intent
import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.scanner.MainViewModel
import com.example.scanner.NavigationUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannedTextScreen(
    scannedText: String,
    onNavigate: (NavigationUiEvent) -> Unit
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Scanned Text")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigate(NavigationUiEvent.NavigateToMainScreen)
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomAppBarItems.entries.forEach { item ->
                        IconButtonWithText(icon = item.icon, text = item.title) {
//                            onBottomBarEvent(item.event)
                        }
                    }
                }
            }

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = scannedText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
            )
        }
    }

}

@Composable
fun IconButtonWithText(
    icon: ImageVector,
    text: String,
    onClick: (BottomBarUiEvent) -> Unit
) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick(BottomBarUiEvent.Copy) }
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }

}

enum class BottomAppBarItems(
    val icon: ImageVector,
    val title: String,
    val event: BottomBarUiEvent
) {
    Copy(icon = Icons.Outlined.FileCopy, title = "Copy", event = BottomBarUiEvent.Copy),
    Share(icon = Icons.Outlined.Share, title = "Share", event = BottomBarUiEvent.Share),
    Save(icon = Icons.Outlined.Save, title = "Save", event = BottomBarUiEvent.Save),
    Translate(icon = Icons.Outlined.Translate, title = "Translate", event = BottomBarUiEvent.Translate),
}


@Composable
fun ShareText(text: String) {
    val context = LocalContext.current
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)
}


sealed class BottomBarUiEvent {
    data object Copy : BottomBarUiEvent()
    data object Share: BottomBarUiEvent()
    data object Save : BottomBarUiEvent()
    data object Translate : BottomBarUiEvent()
}

class BottomBarEvent() {
    private fun copyToClipboard(text: String) {
        // Logic to copy text to clipboard
    }

    private fun shareText(text: String) {
        // Logic to share text
    }

    private fun saveText(text: String) {
        // Logic to save text
    }

    private fun translateText(text: String) {
        // Logic to translate text
    }
}

@Preview
@Composable
private fun ScannedTextPreview() {
//    IconButtonWithText(icon = Icons.Outlined.FileCopy, text = "Copy")
    ScannedTextScreen(
        scannedText = "ahsan"
    ) {
        NavigationUiEvent.NavigateBack
    }
}



