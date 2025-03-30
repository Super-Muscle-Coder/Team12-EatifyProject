package com.example.appbanhangonline.views.productDetailScreen.subfuncDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.ui.Alignment

@Composable
fun DescriptionBox(
    description: String,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    // Giới hạn tối đa là 10% chiều cao màn hình
    val maxBoxHeight = screenHeight * 0.10f

    var expanded by remember { mutableStateOf(false) }
    val boxHeight = if (expanded) Modifier.wrapContentHeight() else Modifier.height(maxBoxHeight)

    Column(
        modifier = modifier.then(boxHeight)
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    // Nếu nội dung vượt quá giới hạn, hiển thị mũi tên để mở rộng/kép gọn.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(top = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = if (expanded) "Thu gọn mô tả" else "Mở rộng mô tả",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}
