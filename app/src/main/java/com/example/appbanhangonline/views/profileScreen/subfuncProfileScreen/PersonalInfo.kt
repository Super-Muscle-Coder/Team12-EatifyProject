package com.example.appbanhangonline.views.profileScreen.subfuncProfileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PersonalInfo(navController: NavHostController, userId: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        MenuOption(  //Sản phẩm yêu thích
            navController = navController,
            title = "Your Favorites",
            iconId = R.drawable.favourite,
            route = "favouritesScreen"
        )
        MenuOption( //Phương thức thanh toán
            navController = navController,
            title = "Payment",
            iconId = R.drawable.wallet,
            route = "paymentScreen"
        )
        MenuOption( //Thay đổi thông tin cá nhân
            navController = navController,
            title = "Edit Profile",
            iconId = R.drawable.edit,
            route = "editProfileScreen/$userId"
        )
        MenuOption(  //Cài đặt ( ví dụ như chế độ giao diện, ngôn ngữ
            navController = navController,
            title = "Settings",
            iconId = R.drawable.settings,
            route = "settingsScreen"
        )
        MenuOption(  //Nhận tư vấn khách hàng
            navController = navController,
            title = "Help",
            iconId = R.drawable.help,
            route = "helpScreen"
        )
        MenuOption(  //Đăng xuất
            navController = navController,
            title = "Log Out",
            iconId = R.drawable.logout,
            route = "logOut"
        )
    }
}

@Composable
fun MenuOption(
    navController: NavHostController,
    title: String,
    iconId: Int,
    route: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (route == "logOut") {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("loginScreen") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                } else {
                    navController.navigate(route)
                }
            }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "$title Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.arrowright),
            contentDescription = "Arrow Icon",
            modifier = Modifier.size(20.dp)
        )
    }
}
