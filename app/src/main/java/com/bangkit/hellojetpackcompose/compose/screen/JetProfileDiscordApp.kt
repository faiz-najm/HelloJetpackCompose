package com.bangkit.hellojetpackcompose.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.hellojetpackcompose.compose.navigation.Screen
import com.bangkit.hellojetpackcompose.compose.screen.about.AboutScreen
import com.bangkit.hellojetpackcompose.compose.screen.detail.DetailScreen
import com.bangkit.hellojetpackcompose.compose.screen.favorite.FavoriteScreen
import com.bangkit.hellojetpackcompose.compose.screen.home.HomeScreen
import com.bangkit.hellojetpackcompose.compose.theme.JetProfileDiscordTheme

@Composable
fun JetProfileDiscordApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { username ->
                    navController.navigate(Screen.DetailProfile.createRoute(username))
                },
                navigateToFavorite = {
                    navController.navigate(Screen.Favorite.route)
                },
                navigateToAbout = {
                    navController.navigate(Screen.About.route)
                }
            )
        }

        composable(
            route = Screen.DetailProfile.route,
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
            )
        ) {
            /*it.arguments?.getString("username")?.let { jsonString ->
                val user = jsonString.fromJson(Items::class.java)

            }*/
            DetailScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )

        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navigateToDetail = { username ->
                    navController.navigate(Screen.DetailProfile.createRoute(username))
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.About.route) {
            AboutScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetProfileDiscordTheme {
        JetProfileDiscordApp()
    }
}