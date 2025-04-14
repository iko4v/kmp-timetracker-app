package org.company.app.presentation.screens.login

sealed interface LoginEvent {
    data object OnLogin : LoginEvent

    data object OnRegister : LoginEvent

    data class OnEmailChanged(val value: String) : LoginEvent

    data class OnPasswordChanged(val value: String) : LoginEvent

    data object OnSetDefaultState : LoginEvent
}