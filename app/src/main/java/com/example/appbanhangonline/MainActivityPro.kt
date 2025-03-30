package com.example.appbanhangonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appbanhangonline.views.loginandregisterScreen.LogOrRegScreen
import com.example.appbanhangonline.views.providerSpace.LoadingBusiness
import com.example.appbanhangonline.views.providerSpace.ProviderLogScreen
import com.example.appbanhangonline.views.providerSpace.productManage.ProductManageScreen
import com.example.appbanhangonline.views.providerSpace.providerHomeScreen.ProviderHomeScreen
import com.example.appbanhangonline.views.providerSpace.templateGeneralPro.MainScaffoldPro

class MainActivityPro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ẩn thanh trạng thái và thanh điều hướng
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars()) // Ẩn toàn bộ system bars
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE // Vuốt để hiện lại
        }

        setContent {
            val navController = rememberNavController()
            MyApp(navController = navController)
        }
    }
}

@Composable
fun MyApp(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Logic kiểm tra hiển thị NavBarPro
    val showBottomBar = currentRoute?.contains("providerHomeScreen") ?: false

    MainScaffoldPro(
        navController = navController,
        showBottomBar = showBottomBar, // Control visibility of NavBarPro
        content = {
            NavHost(
                navController = navController,
                startDestination = "providerLoginScreen",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("logOrRegScreen") {
                    LogOrRegScreen(navController = navController)
                }
                composable("providerLoginScreen") {
                    ProviderLogScreen(navController = navController)
                }
                composable(
                    route = "loadingBusinessScreen/{email}/{password}",
                    arguments = listOf(
                        navArgument("email") { type = NavType.StringType },
                        navArgument("password") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val email = backStackEntry.arguments?.getString("email") ?: ""
                    val password = backStackEntry.arguments?.getString("password") ?: ""
                    LoadingBusiness(onContinue = {
                        navController.navigate("providerHomeScreen/$email/$password") {
                            popUpTo("providerLoginScreen") { inclusive = true }
                        }
                    })
                }
                composable(
                    route = "providerHomeScreen/{email}/{password}",
                    arguments = listOf(
                        navArgument("email") { type = NavType.StringType },
                        navArgument("password") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val email = backStackEntry.arguments?.getString("email") ?: ""
                    val password = backStackEntry.arguments?.getString("password") ?: ""
                    ProviderHomeScreen(email = email, password = password)
                }
                composable(
                    route = "productManageScreen/{email}/{password}",
                    arguments = listOf(
                        navArgument("email") { type = NavType.StringType },
                        navArgument("password") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val email = backStackEntry.arguments?.getString("email") ?: ""
                    val password = backStackEntry.arguments?.getString("password") ?: ""
                    ProductManageScreen() // Bạn có thể thêm các thông số nếu cần truyền vào
                }
            }
        }
    )
}