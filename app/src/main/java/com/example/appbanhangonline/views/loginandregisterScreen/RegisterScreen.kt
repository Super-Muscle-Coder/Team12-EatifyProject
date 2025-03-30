@file:Suppress("DEPRECATION")

package com.example.appbanhangonline.views.loginandregisterScreen

import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.style.TextOverflow
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R
import com.example.appbanhangonline.viewmodels.loginandregisterViewModels.RegisterViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle

@Composable
fun RegisterScreen(navController: NavHostController, showLoadingOverlay: (Boolean) -> Unit, registerViewModel: RegisterViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val iconSize = screenWidth * 0.25f
    val headlineFontSize = with(density) { (screenWidth * 0.05f + screenHeight * 0.02f).toSp() }
    val mediumTextFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.012f).toSp() }
    val buttonFontSize = with(density) { (screenWidth * 0.025f + screenHeight * 0.015f).toSp() }
    val topSpacer = screenHeight * 0.065f
    val middleSpacer = screenHeight * 0.05f
    val bottomSpacer = screenHeight * 0.045f

    val textColor = MaterialTheme.colorScheme.onBackground
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val phoneNumber by remember { mutableStateOf("") }
    val address by remember { mutableStateOf("") }
    var showText by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }
    var showImage by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showImage = true
        showText = true
        showButtons = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        //.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier= Modifier.weight(0.005f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = textColor,
                    modifier = Modifier.size(screenWidth * 0.05f)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(topSpacer))

            AnimatedVisibility(
                visible = showImage,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                        scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.registeracc), // Thay thế bằng ID ảnh icon của bạn
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                visible = showText,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000))
            ) {
                Text(
                    text = "Get started",
                    color = textColor,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = headlineFontSize,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(middleSpacer))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                placeholder = { Text("Your Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(50), // Bo tròn góc
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(3.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("Usersname@example.com") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(50), // Bo tròn góc
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(3.dp))

            TextField(  //Chỗ nhập mật khẩu
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("**********") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(50), // Bo tròn góc
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (passwordVisible) painterResource(id = R.drawable.openeye) else painterResource(id = R.drawable.closeeye)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, contentDescription = if (passwordVisible) "Hide password" else "Show password", modifier = Modifier.size(24.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.height(3.dp))

            TextField(   //Chỗ xác nhận mật khẩu
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                placeholder = { Text("**********") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(50), // Bo tròn góc
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) painterResource(id = R.drawable.openeye) else painterResource(id = R.drawable.closeeye)
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(painter = image, contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password", modifier = Modifier.size(24.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.height(bottomSpacer))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (password == confirmPassword) {
                            registerViewModel.registerUser(name, email, password, phoneNumber, address, "") { success, message ->
                                if (success) {
                                    // Bật LoadingOverlay
                                    showLoadingOverlay(true)
                                    // Điều hướng đến HomeScreen với popUpTo
                                    navController.navigate("homeScreen") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Registration failed: $message", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(50))
                ) {
                    Text(text = "Sign up", color = Color.White, fontSize = buttonFontSize)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Sign up with", color = textColor)

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { /* Handle Facebook sign up */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Facebook",
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(onClick = { /* Handle Twitter sign up */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.twitter),
                            contentDescription = "Twitter",
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(onClick = { /* Handle Google sign up */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google",
                            tint = Color.Unspecified
                        )
                    }

                }

                Spacer(modifier = Modifier.height(15.dp))

                val annotatedText = buildAnnotatedString {
                    append("Already have an account? ")

                    // Phần chữ "Sign in" có vai trò như một nút bấm và có màu xanh ngọc
                    pushStringAnnotation(tag = "SIGNIN", annotation = "sign_in")
                    withStyle(style = SpanStyle(color = Color(0xFF87CEEB))) {
                        append("Sign in")
                    }
                    pop()
                }
                Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                    ClickableText(
                        text = annotatedText,
                        onClick = { offset ->
                            annotatedText.getStringAnnotations(tag = "SIGNIN", start = offset, end = offset)
                                .firstOrNull()?.let {
                                    navController.navigate("loginScreen")
                                }
                        },
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = mediumTextFontSize,
                            color = MaterialTheme.colorScheme.onBackground),
                        overflow = TextOverflow.Clip,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))

            }
        }
    }
}
