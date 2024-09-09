package com.srabbijan.dailycost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.srabbijan.dailycost.navigation.AppNavigation
import com.srabbijan.dailycost.navigation.NavigationSubGraphs
import com.srabbijan.design.theme.AppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val navigationSubGraphs: NavigationSubGraphs by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                AppNavigation(navigationSubGraphs = navigationSubGraphs)
            }
        }
    }
}
