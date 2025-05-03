package com.example.healthifyai.ui.theme.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import com.example.healthifyai.ui.auth.HomeScreen
import com.example.healthifyai.ui.auth.ProfileScreen
import com.example.healthifyai.ui.navigation.Screen

@Composable
fun MainNavigation(onSignOut: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val entry by navController.currentBackStackEntryAsState()
                val current = entry?.destination?.route

                listOf(Screen.Home, Screen.Profile).forEach { screen ->
                    NavigationBarItem(
                        selected = current == screen.route,
                        onClick = {
                            if (current != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        label = { Text(screen.title) },
                        icon = {} // add icons here if you like
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onSignOut = onSignOut,
                    onProfileClick = { navController.navigate(Screen.Profile.route) }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onSignOut = onSignOut)
            }
        }
    }
}
