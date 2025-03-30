package com.example.appbanhangonline.views.profileScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.profileViewModels.ProfileViewModel
import com.example.appbanhangonline.viewmodels.navBarViewModels.NavBarViewModel
import com.example.appbanhangonline.views.profileScreen.subfuncProfileScreen.BasicInfo
import com.example.appbanhangonline.views.profileScreen.subfuncProfileScreen.PersonalInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, userId: String) {
    val viewModel: ProfileViewModel = viewModel()
    val navBarViewModel: NavBarViewModel = viewModel()

    LaunchedEffect(userId) {
        viewModel.fetchUserById(userId)
        navBarViewModel.selectIndex(2)  // Cập nhật trạng thái của thanh điều hướng khi màn hình Profile được hiển thị
    }

    val user by viewModel.user.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        user?.let {
            BasicInfo(
                name = it.name,
                email = it.email,
                phoneNumber = it.phoneNumber,
                avatarUrl = it.avatarUrl,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            PersonalInfo(
                navController = navController,
                userId = userId,  // Truyền userId vào PersonalInfo
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
