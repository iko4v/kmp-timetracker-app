package org.company.app.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.koin.core.component.KoinComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.company.app.domain.model.AppUser
import org.company.app.presentation.screens.registration.RegistrationEvent


class LoginViewModel : ViewModel(), KoinComponent {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
            // LoginState()
        )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> doLogin(event)
            is LoginEvent.OnRegister -> doRegister(event)
            is LoginEvent.OnEmailChanged -> doEmailChanged(event)
            is LoginEvent.OnPasswordChanged -> doPasswordChanged(event)
            is LoginEvent.OnSetDefaultState -> setDefaultState()
        }
    }

    private fun doLogin(event: LoginEvent.OnLogin) {
        when {
            _state.value.emailText.isBlank() || _state.value.emailText.isEmpty() -> {
                _state.update {
                    it.copy(
                        emailError = "Email blank или empty"
                    )
                }
            }

            _state.value.passwordText.isBlank() || _state.value.passwordText.isEmpty() -> {
                _state.update {
                    it.copy(
                        passwordError = "Пароль blank или empty"
                    )
                }
            }

            else -> {
                // TODO: В этом месте логинимся.

                // Объект для логина
                val user = AppUser(
                    email = _state.value.emailText,
                    password = _state.value.passwordText,
                    name = "",
                    registrationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .toString()
                )

                println("user: $user")
                // TODO: Добавить логику логина.
            }
        }
    }

    private fun doRegister(even: LoginEvent.OnRegister) {
        println("doRegister")
    }

    private fun doEmailChanged(event: LoginEvent.OnEmailChanged){
        _state.update {
            it.copy(
                emailText = event.value,
                emailError = null,
            )
        }
    }

    private fun doPasswordChanged(event: LoginEvent.OnPasswordChanged) {
        _state.update {
            it.copy(
                passwordText = event.value,
                passwordError = null,
            )
        }
    }

    private fun setDefaultState() {
        _state.update {
            it.copy(
                isLoginSuccess = false,
                emailText = "",
                passwordText = "",
                emailError = null,
                passwordError = null,
                isLoading = false,
                errorMessage = null
            )
        }
    }
}