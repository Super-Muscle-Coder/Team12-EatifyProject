package com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel

@Composable
fun SearchBar(
    searchTerm: String,
    onSearchTermChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    navController: NavHostController
) {
    var textFieldValue by remember { mutableStateOf(searchTerm) }
    val productViewModel: ProductViewModel = viewModel()
    val suggestions by productViewModel.products.observeAsState(initial = listOf())

    // Derived state for DropdownMenu expansion
    val isDropdownExpanded by remember {
        derivedStateOf {
            val lowerValue = textFieldValue.lowercase().trim()
            lowerValue.isNotBlank() && suggestions.any { it.nameLowercase.contains(lowerValue) }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            // Input Field
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    val lowerValue = newValue.lowercase().trim()
                    onSearchTermChange(lowerValue)
                    onSearch(lowerValue)
                    println("DEBUG: TextField value updated to '$lowerValue'")
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
                placeholder = { Text(text = "Search for some food?", fontSize = 16.sp) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .height(56.dp)
            )

            // DropdownMenu
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { /* no-op: derived state manages expanded status */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp)
                    .padding(horizontal = 8.dp)
            ) {
                val filteredSuggestions = suggestions.filter { product ->
                    val isMatch = product.nameLowercase.contains(textFieldValue.lowercase().trim())
                    println("DEBUG: Filtering product '${product.name}', matches: $isMatch")
                    isMatch
                }
                println("DEBUG: Filtered suggestions count = ${filteredSuggestions.size}")

                filteredSuggestions.forEach { product ->
                    DropdownMenuItem(
                        text = {
                            Text(text = product.name, fontSize = 16.sp)
                        },
                        onClick = {
                            val lowerValue = product.nameLowercase
                            textFieldValue = lowerValue
                            onSearchTermChange(lowerValue)
                            println("DEBUG: Dropdown item clicked -> Navigating to '$lowerValue'")
                            navController.navigate("searchScreen/$lowerValue") {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}
