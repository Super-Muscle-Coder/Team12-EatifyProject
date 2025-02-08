package com.example.appbanhangonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat // Import thêm dòng này
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appbanhangonline.IntroduceScreen
import com.example.appbanhangonline.IntroduceScreen1
import com.example.appbanhangonline.IntroduceScreen2
import com.example.appbanhangonline.IntroduceScreen3
import com.example.appbanhangonline.ui.theme.AppBanHangOnlineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // Cấu hình để giao diện chiếm toàn bộ màn hình
        setContent {
            AppBanHangOnlineTheme {
                MyApp()
            }
        }

        // Ẩn thanh thông báo và thanh điều hướng
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "introduceScreen") {
        composable("introduceScreen") { IntroduceScreen(navController) }
        composable("introduceScreen1") { IntroduceScreen1(navController) }
        composable("introduceScreen2") { IntroduceScreen2(navController) }
        composable("introduceScreen3") { IntroduceScreen3(navController) }
    }
}
