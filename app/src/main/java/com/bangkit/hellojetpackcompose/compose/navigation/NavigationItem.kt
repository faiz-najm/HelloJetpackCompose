package com.dicoding.jetreward.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.bangkit.hellojetpackcompose.compose.navigation.Screen

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)
