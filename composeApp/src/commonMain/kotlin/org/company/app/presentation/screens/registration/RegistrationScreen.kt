package org.company.app.presentation.screens.registration

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import timetracker_pro.composeapp.generated.resources.IndieFlower_Regular
import timetracker_pro.composeapp.generated.resources.Res
import timetracker_pro.composeapp.generated.resources.app_title
import timetracker_pro.composeapp.generated.resources.button_registration

@Composable
fun Registration(
    modifier: Modifier = Modifier
) {
    val viewModel: RegistrationViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(32.dp)
    ) {
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
            label = { Text("Имя") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Name icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.emailText,
            onValueChange = {
                viewModel.onEvent(RegistrationEvent.OnEmailChanged(it))
            },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
            label = { Text("Пароль") },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                .height(48.dp)
        ) {
            Text(
                text = stringResource(Res.string.button_registration)
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
}
