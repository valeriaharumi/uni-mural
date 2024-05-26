package com.github.valeriaharumi.challenge.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.R
import com.github.valeriaharumi.challenge.ui.components.Input

@Composable
fun LoginScreen(loginViewModel : LoginViewModel, navController : NavController) {
    val email by loginViewModel.emailState.observeAsState(initial = "")
    val password by loginViewModel.passwordState.observeAsState(initial = "")
    val isLoading by loginViewModel.isLoadingState.observeAsState(initial = false)
    val errorMessage by loginViewModel.errorMessageState.observeAsState(initial = "")
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.bora_somar),
            contentDescription = "splash",
            modifier = Modifier.size(250.dp)
        )
        Input(
            value = email,
            placeholder = "email@email.com",
            label = "Qual o seu email?",
            modifier = Modifier,
            keyboardType = KeyboardType.Email,
            visualTransformation = VisualTransformation.None
        ) {
            loginViewModel.onEmailChanged(it)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Input(
            value = password,
            placeholder = "",
            label = "Senha",
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
            loginViewModel.onPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loginViewModel.login(email, password, navController)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { navController.navigate("register") }
        ) {
            Text("Ainda não tem uma conta? Criar conta")
        }
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}