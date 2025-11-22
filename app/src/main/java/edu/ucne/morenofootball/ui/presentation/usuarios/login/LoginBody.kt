package edu.ucne.morenofootball.ui.presentation.usuarios.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.morenofootball.R
import edu.ucne.morenofootball.ui.presentation.composables.ErrorMessage

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {},
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToHome.collect {
            navigateToHome()
        }
    }

    LoginBody(
        state = state.value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun LoginBody(
    state: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo_no_background),
                contentDescription = "Logo de la app",
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Inside
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Campo de email
        TextField(
            value = state.email,
            onValueChange = { onEvent(LoginUiEvent.OnEmailChange(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            enabled = !state.isLoading
        )
        ErrorMessage(state.errorEmail)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña con ojito
        TextField(
            value = state.password,
            onValueChange = { onEvent(LoginUiEvent.OnPasswordChange(it)) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onEvent(LoginUiEvent.Login(state.email, state.password))
                }
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null
                )
            },
            enabled = !state.isLoading,
            trailingIcon = {
                val image = if (state.isPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                val description =
                    if (state.isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { onEvent(LoginUiEvent.OnTogglePasswordVisibility) }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description
                    )
                }
            }
        )
        ErrorMessage(state.errorPass)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onEvent(LoginUiEvent.Login(state.email, state.password)) },
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (!state.isLoading) Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.titleMedium
            ) else {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ErrorMessage(state.message)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginBody(
        state = LoginUiState(),
        onEvent = { }
    )
}