package com.github.valeriaharumi.challenge.ui.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.ui.components.Input
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel, navController: NavController) {
    val name by registerViewModel.nameState.observeAsState(initial = "")
    val email by registerViewModel.emailState.observeAsState(initial = "")
    val password by registerViewModel.passwordState.observeAsState(initial = "")
    val confirmPassword by registerViewModel.confirmPasswordState.observeAsState(initial = "")
    val isLoading by registerViewModel.isLoadingState.observeAsState(initial = false)
    val errorMessage by registerViewModel.errorMessageState.observeAsState(initial = "")
    val termsAccepted by registerViewModel.termsAcceptedState.observeAsState(initial = false)
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Input(
            value = name,
            placeholder = "",
            label = "Digite seu nome",
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None
        ) {
            registerViewModel.onNameChanged(it)
        }
        Input(
            value = email,
            placeholder = "email@email.com",
            label = "Digite seu email",
            modifier = Modifier,
            keyboardType = KeyboardType.Email,
            visualTransformation = VisualTransformation.None
        ) {
            registerViewModel.onEmailChanged(it)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Input(
            value = password,
            placeholder = "",
            label = "Digite uma senha",
            modifier = Modifier,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        ){
            registerViewModel.onPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Input(
            value = confirmPassword,
            placeholder = "",
            label = "Confirme a Senha",
            modifier = Modifier,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        ) {
            registerViewModel.onConfirmPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { registerViewModel.onTermsAcceptedChanged(it) }
            )
            Text(
                text = "Eu aceito os termos e condições."
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ver termos e condições",
                color = Color.Blue,
                modifier = Modifier.clickable { navController.navigate("terms") }
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (registerViewModel.validateForm()) {
                    registerViewModel.register(email, password, name, navController)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Registrar")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}