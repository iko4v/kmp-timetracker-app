package org.company.app.presentation.screens.registration

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import org.company.app.presentation.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import timetracker_pro.composeapp.generated.resources.IndieFlower_Regular
import timetracker_pro.composeapp.generated.resources.Res
import timetracker_pro.composeapp.generated.resources.app_title
import timetracker_pro.composeapp.generated.resources.button_do_registration
import timetracker_pro.composeapp.generated.resources.button_login
import timetracker_pro.composeapp.generated.resources.button_registration
import timetracker_pro.composeapp.generated.resources.email
import timetracker_pro.composeapp.generated.resources.ic_dark_mode
import timetracker_pro.composeapp.generated.resources.ic_light_mode
import timetracker_pro.composeapp.generated.resources.name
import timetracker_pro.composeapp.generated.resources.password

@Composable
fun Registration(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
) {
    val viewModel: RegistrationViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()

    when {
        // isLoading
        viewModel.state.value.isLoading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Color.Red)
            }
        }
        // isRegistrationSuccess
        viewModel.state.value.isRegistrationSuccess -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Registration success")
            }
        }
        // errorMessage
        viewModel.state.value.errorMessage.isNullOrEmpty().not() -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = uiState.errorMessage.orEmpty(),
                    color = Color.Red
                )
            }
        }
        // Default
        else -> {
            RegistrationScreenContent(uiState, viewModel)
        }
    }


}

@Composable
private fun RegistrationScreenContent(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(Res.string.button_registration),
                fontSize = 15.sp,
                style = MaterialTheme.typography.displaySmall,
            )

            var isDark by LocalThemeIsDark.current
            val icon = remember(isDark) {
                if (isDark) Res.drawable.ic_light_mode
                else Res.drawable.ic_dark_mode
            }

            ElevatedButton(
                onClick = { isDark = !isDark },
                content = {
                    Icon(vectorResource(icon), contentDescription = null)
                    //Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            )
        }

        Text(
            text = stringResource(Res.string.app_title),
            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(top = 80.dp, bottom = 30.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)

        )

        OutlinedTextField(
            value = uiState.nameText,
            onValueChange = {
                viewModel.onEvent(RegistrationEvent.OnNameChanged(it))
            },
            label = { Text(stringResource(Res.string.name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Name icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
                .focusable()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.emailText,
            onValueChange = {
                viewModel.onEvent(RegistrationEvent.OnEmailChanged(it))
            },
            label = { Text(stringResource(Res.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.passwordText,
            onValueChange = {
                viewModel.onEvent(RegistrationEvent.OnPasswordChanged(it))
            },
            label = { Text(stringResource(Res.string.password)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) FeatherIcons.Eye
                        else FeatherIcons.EyeOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(RegistrationEvent.OnRegistration) // Event отсюда: RegistrationEvent.kt
            },
            enabled = isButtonEnable(uiState),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.button_do_registration)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onLoginClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.button_login)
            )
        }
    }
}

/**
 * Функция для проверки enable/disable кнопки.
 */
fun isButtonEnable(uiState: RegistrationState): Boolean {
    return uiState.emailText.isNotEmpty() && uiState.emailText.isNotBlank()
            && uiState.passwordText.isNotEmpty() && uiState.passwordText.isNotBlank()
            && uiState.nameText.isNotEmpty() && uiState.nameText.isNotBlank()
}
