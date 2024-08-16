package com.example.scanner.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavController
import com.example.scanner.Routes
import kotlinx.coroutines.delay


@Composable
fun WelcomeScreen(navController: NavController) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        visible = false
        delay(300)
        navController.navigate(Routes.MAINSCREEN) {
            popUpTo(Routes.WELCOMESCREEN) { inclusive = true }
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it / 2 }), // + slideInHorizontally(initialOffsetX = { it / 2 }),
        exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { -it / 2 }) // + slideOutHorizontally(targetOffsetX = { -it / 2 })
    ) {
        Welcome()
    }
}

@Composable
fun Welcome() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Scanner App", style = typography.displayLarge, fontStyle = FontStyle.Italic, color = Color.Black)
    }

}