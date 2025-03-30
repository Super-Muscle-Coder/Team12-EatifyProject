package com.example.appbanhangonline.views.providerSpace.templateGeneralPro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainScaffoldPro(
    navController: NavHostController,
    showBottomBar: Boolean, // Quản lý hiển thị NavBarPro
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) { // Chỉ hiển thị NavBarPro khi `showBottomBar` = true
                NavBarPro(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            content()
        }
    }
}