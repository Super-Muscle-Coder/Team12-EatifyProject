package com.example.appbanhangonline.views.templateGeneral

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.navBarViewModels.NavBarViewModel

@Composable
fun MainScaffold(
    navController: NavHostController,
    navBarViewModel: NavBarViewModel,
    showBottomBar: Boolean,
    content: @Composable () -> Unit
) {
    // Đồng bộ `NavController` với `NavBarViewModel`
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val currentRoute = backStackEntry.destination.route
            val index = when {
                currentRoute?.startsWith("homeScreen") == true -> 0
                currentRoute?.startsWith("cartScreen") == true -> 1
                currentRoute?.startsWith("orderScreen") == true -> 2
                currentRoute?.startsWith("profileScreen") == true -> 3
                else -> -1
            }
            if (index != -1) {
                navBarViewModel.selectIndex(index)
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavBar(
                    navController = navController,
                    navBarViewModel = navBarViewModel
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .zIndex(0f)
        ) {
            content()
        }
    }
}
