package com.github.valeriaharumi.challenge.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val emailState: LiveData<String> = _email

    fun onEmailChanged(newEmail: String){
        _errorMessageState.value = ""
        _email.value = newEmail
    }

    private val _password = MutableLiveData<String>()
    val passwordState: LiveData<String> = _password

    fun onPasswordChanged(newPassword: String){
        _errorMessageState.value = ""
        _password.value = newPassword
    }

    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> get() = _isLoadingState

    private val _errorMessageState = MutableLiveData("")
    val errorMessageState: LiveData<String> get() = _errorMessageState

    fun login(email: String, password: String, navController: NavController) {
        _isLoadingState.value = true
        viewModelScope.launch {
            val user = authRepository.login(email, password)

            if (user != null) {
                navController.navigate("board")
            } else {
                _errorMessageState.value = "Email ou senha inválidos"
                _isLoadingState.value = false
            }
        }
    }
}