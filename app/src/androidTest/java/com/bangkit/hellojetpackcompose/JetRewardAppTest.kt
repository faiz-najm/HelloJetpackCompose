package com.bangkit.hellojetpackcompose

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.bangkit.hellojetpackcompose.compose.navigation.Screen
import com.bangkit.hellojetpackcompose.compose.screen.JetProfileDiscordApp
import com.bangkit.hellojetpackcompose.compose.theme.JetProfileDiscordTheme
import com.bangkit.hellojetpackcompose.model.FakeUserDataSource
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetProfileDiscordTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetProfileDiscordApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("UserList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeUserDataSource.dummyUser[10].login).performClick()
        navController.assertCurrentRouteName(Screen.DetailProfile.route)
        composeTestRule.onNodeWithText(FakeUserDataSource.dummyUser[10].login).assertIsDisplayed()
    }
}