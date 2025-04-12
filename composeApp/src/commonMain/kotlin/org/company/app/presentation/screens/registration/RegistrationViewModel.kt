package org.company.app.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.company.app.domain.model.AppUser
import org.koin.core.component.KoinComponent
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class RegistrationViewModel : ViewModel(), KoinComponent {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
            // RegistrationState()
        )

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnRegistration -> doRegistration(event)
            is RegistrationEvent.OnEmailChanged -> doEmailChanged(event)
            is RegistrationEvent.OnPasswordChanged -> doPasswordChanged(event)
            is RegistrationEvent.OnNameChanged -> doNameChanged(event)
            is RegistrationEvent.OnSetDefaultState -> setDefaultState()
        }
    }

    private fun doRegistration(event: RegistrationEvent.OnRegistration) {
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
                // TODO: В этом месте регистрирем пользователя.

                // Объект для регистрации
                val user = AppUser(
                    email = _state.value.emailText,
                    password = _state.value.passwordText,
                    name = _state.value.nameText,
                    registrationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .toString()
                )

                println("user: $user")
                // TODO: Добавить сохранение пользователя.
            }
        }
    }

    private fun doEmailChanged(event: RegistrationEvent.OnEmailChanged) {
        _state.update {
            it.copy(
                emailText = event.value,
                emailError = null,
            )
        }
    }

    private fun doPasswordChanged(event: RegistrationEvent.OnPasswordChanged) {
        _state.update {
            it.copy(
                passwordText = event.value,
                passwordError = null,
            )
        }
    }

    private fun doNameChanged(event: RegistrationEvent.OnNameChanged) {

    }


    private fun setDefaultState() {
        _state.update {
            it.copy(
                isRegistrationSuccess = false,
                emailText = "",
                passwordText = "",
                nameText = "",
                emailError = null,
                passwordError = null,
                isLoading = false,
                errorMessage = null
            )
        }
    }
}