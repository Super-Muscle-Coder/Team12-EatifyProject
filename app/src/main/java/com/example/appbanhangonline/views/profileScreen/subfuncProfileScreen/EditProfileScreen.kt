package com.example.appbanhangonline.views.profileScreen.subfuncProfileScreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.appbanhangonline.R
import com.example.appbanhangonline.viewmodels.profileViewModels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    userId: String,
    selectedImageUri: Uri?,
    viewModel: ProfileViewModel = viewModel(),
    onPickImage: () -> Unit  // Lambda để bắt ảnh từ MainActivity
) {
    LaunchedEffect(userId) {
        viewModel.fetchUserById(userId)
    }
    val userData by viewModel.user.observeAsState()

    var name by remember { mutableStateOf(userData?.name ?: "") }
    var phone by remember { mutableStateOf(userData?.phoneNumber ?: "") }
    var address by remember { mutableStateOf(userData?.address ?: "") }

    LaunchedEffect(userData) {
        userData?.let {
            name = it.name
            phone = it.phoneNumber
            address = it.address
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imagePainter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(selectedImageUri ?: userData?.avatarUrl ?: R.drawable.icon)  // Hiển thị avatar mặc định nếu chưa thiết lập
                        .crossfade(true)
                        .error(R.drawable.icon)
                        .build()
                )
                Image(
                    painter = imagePainter,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray, CircleShape)
                        .clickable { onPickImage() },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    placeholder = { Text("Nhập tên đầy đủ của bạn") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    placeholder = { Text("Nhập số điện thoại của bạn") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    placeholder = { Text("Nhập địa chỉ của bạn") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val updatedInfo: Map<String, Any> = mapOf(
                            "name" to name,
                            "phoneNumber" to phone,
                            "address" to address,
                            "avatarUrl" to (selectedImageUri?.toString() ?: userData?.avatarUrl ?: "")
                        )
                        viewModel.updateUser(userId, updatedInfo) { success ->
                            if (success) {
                                navController.popBackStack()
                            } else {
                                // Xử lý lỗi, ví dụ hiển thị Snackbar
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Image(
                        painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.save else R.drawable.save),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Save Changes")
                }
            }
        }
    }
}
