package com.example.appbanhangonline

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.appbanhangonline.database.PreferencesHelper
import com.example.appbanhangonline.ui.theme.AppBanHangOnlineTheme
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.example.appbanhangonline.viewmodels.navBarViewModels.NavBarViewModel
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel
import com.example.appbanhangonline.views.homeScreen.HomeScreen
import com.example.appbanhangonline.views.introduceScreen.*
import com.example.appbanhangonline.views.loadingScreen.LoadingOverlay
import com.example.appbanhangonline.views.loginandregisterScreen.LogOrRegScreen
import com.example.appbanhangonline.views.loginandregisterScreen.LoginScreen
import com.example.appbanhangonline.views.loginandregisterScreen.RegisterScreen
import com.example.appbanhangonline.views.productDetailScreen.ProductDetailScreen
import com.example.appbanhangonline.views.profileScreen.ProfileScreen
import com.example.appbanhangonline.views.profileScreen.subfuncProfileScreen.EditProfileScreen
import com.example.appbanhangonline.views.templateGeneral.MainScaffold
import com.example.appbanhangonline.views.categoryScreen.CartScreen
import com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen.CartBubbleScreen
import com.example.appbanhangonline.views.orderScreen.CartConfirmationScreen
import com.example.appbanhangonline.views.orderScreen.OrderScreen
import com.example.appbanhangonline.views.orderScreen.OrderConfirmationScreen
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.InvoiceDetailScreen
import com.example.appbanhangonline.views.searchScreen.SearchScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var imageUri: MutableState<Uri?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Cấu hình Firestore
        val firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        FirebaseFirestore.getInstance().firestoreSettings = firestoreSettings

        // Đăng ký ActivityResultLauncher để chọn ảnh từ thư viện
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imageUri.value = result.data?.data
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppBanHangOnlineTheme {
                MyApp()
            }
        }

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    // Hàm này sẽ được truyền đến EditProfileScreen để kích hoạt việc chọn ảnh
    fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Biến trạng thái đăng nhập
        val currentUser = auth.currentUser

        // Lấy userID từ FirebaseAuth
        val userID = currentUser?.uid ?: ""

        // Biến trạng thái cho LoadingScreen
        var showLoadingOverlay by rememberSaveable { mutableStateOf(false) }

        // Kiểm tra các màn hình cho phép hiển thị CartBubbleScreen
        val showCartBubble = currentRoute?.let { route ->
            route.contains("homeScreen") ||
                    route.contains("cartScreen") ||
                    route.contains("orderScreen") // Bổ sung các màn hình cần thiết
        } ?: false

        // Sử dụng contains để xác định route chính
        val showBottomBar = currentRoute?.let { route ->
            (route.contains("homeScreen") ||
                    route.contains("cartScreen") ||
                    route.contains("profileScreen") ||
                    route.contains("editProfileScreen") ||
                    route.contains("orderScreen")) && !showLoadingOverlay // Thêm orderScreen vào danh sách
        } ?: false

        val context = this
        val productViewModel: ProductViewModel = viewModel()
        val navBarViewModel: NavBarViewModel = viewModel()

        // Thêm CartViewModel để dùng chung giữa các màn hình
        val cartViewModel: CartViewModel = viewModel()

        val isFirstLaunch = PreferencesHelper.isFirstLaunch(context)
        val isRememberMe = PreferencesHelper.isRememberMe(context)


        val startDestination = when {
            isFirstLaunch -> "introduceScreen"
            currentUser != null && isRememberMe -> "homeScreen"
            else -> "logOrRegScreen"
        }
         //Bảo trì xong thì dùng lại

        /*
        val startDestination = "introduceScreen" //Bảo trì xong thì xóa đi hoặc note lại

        if (isFirstLaunch) {
            PreferencesHelper.setFirstLaunch(context, false)
        }*/

        MainScaffold(
            navController = navController,
            navBarViewModel = navBarViewModel,
            showBottomBar = showBottomBar
        ) {
            NavHost(
                navController,
                startDestination = startDestination,
                modifier = Modifier.fillMaxSize().zIndex(0f)
            ) {
                // Các route hiện có
                composable("introduceScreen")  { IntroduceScreen(navController) }
                composable("introduceScreen1") { IntroduceScreen1(navController) }
                composable("introduceScreen2") { IntroduceScreen2(navController) }
                composable("introduceScreen3") { IntroduceScreen3(navController) }
                composable("logOrRegScreen") { LogOrRegScreen(navController) }
                composable("loginScreen") {
                    LoginScreen(navController, showLoadingOverlay = { showLoadingOverlay = it })
                }
                composable("registerScreen") {
                    RegisterScreen(navController, showLoadingOverlay = { showLoadingOverlay = it })
                }
                composable("homeScreen") {
                    HomeScreen(
                        navController = navController,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel
                    )
                }

                composable(
                    route = "productDetail/{key}/{value}",
                    arguments = listOf(
                        navArgument("key") { type = NavType.StringType },
                        navArgument("value") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val key = backStackEntry.arguments?.getString("key") ?: return@composable
                    val value = backStackEntry.arguments?.getString("value") ?: return@composable
                    ProductDetailScreen(
                        navController = navController,
                        key = key,
                        value = value,
                        userID = userID,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel // Truyền CartViewModel xuống ProductDetailScreen
                    )
                }
                composable(
                    route = "cartScreen"
                ) {
                    CartScreen(
                        navController = navController,
                        userID = userID,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel // Truyền CartViewModel xuống CartScreen
                    )
                }
                composable(
                    route = "searchScreen/{searchTerm}",
                    arguments = listOf(navArgument("searchTerm") { type = NavType.StringType })
                ) { backStackEntry ->
                    val searchTerm = backStackEntry.arguments?.getString("searchTerm") ?: ""
                    SearchScreen(
                        navController = navController,
                        userID = userID,
                        searchTerm = searchTerm,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel
                    )
                }

                composable(
                    route = "profileScreen/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
                    ProfileScreen(navController, userId)
                }
                composable(
                    route = "editProfileScreen/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
                    EditProfileScreen(navController, userId, imageUri.value, onPickImage = { pickImage() })
                }

                // Thêm route cho OrderScreen
                composable(
                    route = "orderScreen?selectedTab={selectedTab}",
                    arguments = listOf(navArgument("selectedTab") {
                        type = NavType.IntType
                        defaultValue = 0 // Mặc định là QuickOrderContent nếu không có tham số
                    })
                ) {
                    OrderScreen(
                        navController = navController,
                        userID = userID // Truyền userID xuống OrderScreen
                    )
                }


                // Thêm route cho CartConfirmationScreen
                composable(
                    route = "cartConfirmationScreen?userID={userID}",
                    arguments = listOf(
                        navArgument("userID") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val userIDArgument = backStackEntry.arguments?.getString("userID") ?: return@composable
                    CartConfirmationScreen(
                        navController = navController,
                        cartViewModel = cartViewModel,
                        userID = userIDArgument // Truyền userID vào CartConfirmationScreen
                    )
                }

                // Thêm route cho OrderConfirmationScreen
                composable(
                    route = "orderConfirmationScreen/{productID}",
                    arguments = listOf(
                        navArgument("productID") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val productID = backStackEntry.arguments?.getString("productID")?.toIntOrNull() ?: return@composable
                    val product = productViewModel.getProductById(productID)

                    product?.let {
                        OrderConfirmationScreen(
                            navController = navController,
                            product = it, // Truyền thông tin sản phẩm
                            userID = userID, // Truyền userID
                            orderViewModel = viewModel()
                        )
                    }
                }
                composable(
                    route = "invoiceDetail/{orderID}",
                    arguments = listOf(navArgument("orderID") { type = NavType.StringType })
                ) { backStackEntry ->
                    val orderID = backStackEntry.arguments?.getString("orderID")
                    InvoiceDetailScreen(
                        navController = navController, // Truyền navController vào InvoiceDetailScreen
                        userID = userID,
                        orderID = orderID ?: "",
                        onBackClick = {
                            navController.popBackStack()
                            println("DEBUG: Navigating back from InvoiceDetailScreen")
                        },
                        onNavigateBackToCartTab = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("selectedTab", 1)
                            navController.popBackStack()
                            println("DEBUG: Navigated back to CartOrderContent")
                        }
                    )
                }

            }

            if (showLoadingOverlay) {
                LoadingOverlay(
                    onContinue = {
                        navBarViewModel.selectIndex(0)
                        showLoadingOverlay = false
                        navController.navigate("homeScreen") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxSize().zIndex(1f)
                )
            }

            // Hiển thị CartBubbleScreen chỉ khi điều kiện showCartBubble là true
            if (showCartBubble) {
                CartBubbleScreen(
                    cartContent = {
                        val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
                        if (cartItems.isEmpty()) {
                            Text(text = "Giỏ hàng trống", style = MaterialTheme.typography.bodyLarge)
                        } else {
                            Column {
                                cartItems.forEach { item ->
                                    Text(text = "${item.productID}: ${item.quantity} x ${item.unitPrice} = ${item.totalPrice}")
                                }
                            }
                        }
                    },
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }
        }
    }

}
