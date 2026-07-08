package com.example.zicafe_menu_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.example.zicafe_menu_app.ui.screens.MenuScreen
import com.example.zicafe_menu_app.ui.screens.sampleMenuData
import com.example.zicafe_menu_app.ui.theme.Zicafe_menu_appTheme
import com.example.zicafe_menu_app.ui.viewmodels.MenuViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Zicafe_menu_appTheme(isInDarkTheme = false) {
                val viewModel: MenuViewModel = viewModel()
                val menuData by viewModel.menuData.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    MenuScreen(menuData ?: sampleMenuData)
                }
            }
        }
    }
}
