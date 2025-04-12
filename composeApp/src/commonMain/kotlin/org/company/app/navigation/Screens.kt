package org.company.app.navigation

sealed class Screens(val route: String) {
    object Root : Screens("root")
    object Login : Screens("login")
    object Registration : Screens("registration")
}