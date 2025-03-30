package com.example.appbanhangonline.viewmodels.navBarViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavBarViewModel : ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> get() = _selectedIndex

    fun selectIndex(index: Int) {
        _selectedIndex.value = index
        println("DEBUG: NavBar selected index updated to $index")
    }

}