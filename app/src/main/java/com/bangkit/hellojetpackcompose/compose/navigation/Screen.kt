package com.bangkit.hellojetpackcompose.compose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailProfile : Screen("home/{username}") {
        fun createRoute(username: String) = "home/${username}"
    }

    object Favorite : Screen("favorite")
    object About : Screen("about")
}
