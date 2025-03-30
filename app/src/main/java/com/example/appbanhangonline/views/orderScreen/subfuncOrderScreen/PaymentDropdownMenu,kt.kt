package com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appbanhangonline.R

@Composable
fun PaymentDropdownMenu(
    selectedPaymentMethod: String,
    onPaymentMethodSelected: (String) -> Unit // Gỡ bỏ onSuccessOrder để chỉ tập trung vào lựa chọn phương thức
) {
    var expanded by remember { mutableStateOf(false) }
    val methods = listOf("Cash on Delivery", "Credit Card", "E-Wallet")

    // Lấy icon theo phương thức được chọn
    val selectedIcon = when (selectedPaymentMethod) {
        "Cash on Delivery" -> R.drawable.order
        "Credit Card" -> R.drawable.creditcard
        "E-Wallet" -> R.drawable.wallet
        else -> R.drawable.order
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        OutlinedTextField(
            value = selectedPaymentMethod,
            onValueChange = {},
            readOnly = true,
            label = { Text("Payment Method") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = selectedIcon),
                    contentDescription = selectedPaymentMethod,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Expand Dropdown")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            methods.forEach { method ->
                DropdownMenuItem(
                    onClick = {
                        onPaymentMethodSelected(method) // Gửi phương thức được chọn về hàm gọi
                        expanded = false // Đóng menu
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val iconId = when (method) {
                                "Cash on Delivery" -> R.drawable.order
                                "Credit Card" -> R.drawable.creditcard
                                "E-Wallet" -> R.drawable.wallet
                                else -> R.drawable.order
                            }
                            Icon(
                                painter = painterResource(id = iconId),
                                contentDescription = method,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = method)
                        }
                    }
                )
            }
        }
    }
}

