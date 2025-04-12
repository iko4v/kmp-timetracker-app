package org.company.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.company.app.presentation.screens.login.Login
import org.company.app.presentation.screens.registration.Registration

fun NavGraphBuilder.mainFeatureNavGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = Screens.Login.route,
        route = Screens.Root.route
    ) {
        composable(route = Screens.Login.route) {
            Login(
                onLoginClick = {
                    navHostController.navigate(Screens.Registration.route)
                },
                onRegisterClick = {
                    navHostController.navigate(Screens.Registration.route)
                }
            )
        }

        composable(route = Screens.Registration.route) {
            Registration(
               /* onClick = {
                    navHostController.navigate(MainScreens.Login.route)
                }*/
            )
        }
    }
}
