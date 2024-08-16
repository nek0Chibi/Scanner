package com.example.scanner.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.scanner.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Scanner App") },
                navigationIcon = {
                    Icon(Icons.TwoTone.Menu, contentDescription = "Menu")
                },
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 24.dp, start = 8.dp, end = 8.dp),
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                .background(Color.Gray),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(MenuItems.entries) { menuItem ->
                    GridItem(icon = menuItem.icon, title = menuItem.title) {
                        navController.navigate(menuItem.route)
                    }
                }
            }
        }
    }
}


@Composable
fun GridItem(icon: ImageVector, title: String, onClickMenuItem: () -> Unit) {
    ElevatedCard(
        onClick = onClickMenuItem,
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            FilledIcon(icon = icon, fillColor = Color.Cyan)
            Text(text = title)
        }
    }
}
@Composable
fun FilledIcon(
    icon: ImageVector,
    fillColor: Color
) {
    Surface(
        shape = CircleShape,
        color = fillColor
    ) {
        Box(
            modifier = Modifier.minimumInteractiveComponentSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon,null)
        }
    }
}


@Preview
@Composable
fun ScreenPreview() {

    MainScreen(navController = rememberNavController())

}


enum class MenuItems(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    Camera(icon = Icons.Outlined.CameraAlt, title = "Camera",route = Routes.CAMERASCREEN),
    Gallery(icon = Icons.Outlined.Image, title = "Gallery",route = Routes.CAMERASCREEN),
    SavedFiles(icon = Icons.Outlined.Folder, title = "Saved Files",route = Routes.CAMERASCREEN),
    History(icon = Icons.Outlined.History, title = "History",route = Routes.CAMERASCREEN)

}
