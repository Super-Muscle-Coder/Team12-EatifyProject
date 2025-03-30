package com.example.appbanhangonline.views.profileScreen.subfuncProfileScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Logout(navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = "Log Out Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                // Đăng xuất khỏi Firebase
                FirebaseAuth.getInstance().signOut()
                // Điều hướng về màn hình đăng nhập
                navController.navigate("loginScreen") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        ) {
            Text(text = "Log Out", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
