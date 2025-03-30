package com.example.appbanhangonline.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF212A37), // Oxford Blue
    secondary = Color(0xFF101720), // Smoky Black
    tertiary = Color(0xFF212A37), // Oxford Blue
    background = Color(0xFF212A37), // Oxford Blue
    surface = Color(0xFF212A37), // Oxford Blue
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    outline = Color.White // Thêm màu trắng cho đường viền khi ở chế độ tối
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF87CEEB), // Light Blue
    secondary = Color.LightGray,
    tertiary = Color(0xFF87CEEB), // Light Blue
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    outline = Color.Gray // Màu xám cho đường viền khi ở chế độ sáng
)

@Composable
fun AppBanHangOnlineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
