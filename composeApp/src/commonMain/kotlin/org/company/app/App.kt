package org.company.app

import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.company.app.presentation.theme.AppTheme
import org.company.app.navigation.Screens
import org.company.app.navigation.mainFeatureNavGraph
import org.koin.compose.KoinContext

@Composable
internal fun App() = AppTheme {

    KoinContext {
        val navController: NavHostController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screens.Root.route // Корневой граф.
        ) {
            mainFeatureNavGraph(navHostController = navController) // Вложенный граф.
        }
    }

}