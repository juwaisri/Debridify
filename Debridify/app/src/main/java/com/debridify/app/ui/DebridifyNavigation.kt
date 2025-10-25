package com.debridify.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.debridify.app.ui.screens.*
import com.debridify.app.ui.viewmodel.MainViewModel
import com.debridify.app.ui.viewmodel.UiState

@Composable
fun DebridifyApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()
    val currentProvider by viewModel.currentProvider.collectAsState()
    
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.NeedsProviderSelection -> {
                navController.navigate("provider_selection") {
                    popUpTo(0) { inclusive = true }
                }
            }
            is UiState.NeedsLogin -> {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
            is UiState.Success -> {
                if (navController.currentDestination?.route != "home") {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            else -> {}
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen()
        }
        
        composable("provider_selection") {
            ProviderSelectionScreen(
                onProviderSelected = { provider ->
                    viewModel.setProvider(provider)
                }
            )
        }
        
        composable("login") {
            LoginScreen(
                provider = currentProvider,
                onLogin = { provider, apiKey ->
                    viewModel.setApiKey(provider, apiKey)
                },
                onBackToProviderSelection = {
                    navController.navigate("provider_selection") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onLogout = {
                    viewModel.logout()
                }
            )
        }
    }
}
