package com.github.valeriaharumi.challenge.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.data.model.User
import com.github.valeriaharumi.challenge.data.repository.AuthRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _nameState = MutableLiveData("")
    val nameState: LiveData<String> get() = _nameState

    private val _emailState = MutableLiveData("")
    val emailState: LiveData<String> get() = _emailState

    private val _passwordState = MutableLiveData("")
    val passwordState: LiveData<String> get() = _passwordState

    private val _confirmPasswordState = MutableLiveData("")
    val confirmPasswordState: LiveData<String> get() = _confirmPasswordState

    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> get() = _isLoadingState

    private val _errorMessageState = MutableLiveData("")
    val errorMessageState: LiveData<String> get() = _errorMessageState

    private val _termsAcceptedState = MutableLiveData(false)
    val termsAcceptedState: LiveData<Boolean> get() = _termsAcceptedState

    fun onNameChanged(newName: String) {
        _nameState.value = newName
    }

    fun onEmailChanged(newEmail: String) {
        _emailState.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _passwordState.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPasswordState.value = newConfirmPassword
    }

    fun onTermsAcceptedChanged(accepted: Boolean) {
        _termsAcceptedState.value = accepted
    }

    fun validateForm(): Boolean {
        val email = _emailState.value ?: ""
        val password = _passwordState.value ?: ""
        val confirmPassword = _confirmPasswordState.value ?: ""
        val termsAccepted = _termsAcceptedState.value ?: false

        return when {
            email.isEmpty() -> {
                _errorMessageState.value = "Todos os campos devem ser preenchidos"
                false
            }
            password.isEmpty() -> {
                _errorMessageState.value = "Todos os campos devem ser preenchidos"
                false
            }
            confirmPassword.isEmpty() -> {
                _errorMessageState.value = "Todos os campos devem ser preenchidos"
                false
            }
            password != confirmPassword -> {
                _errorMessageState.value = "As senhas não coincidem"
                false
            }
            !termsAccepted -> {
                _errorMessageState.value = "Para continuar você deve aceitar os termos e condições"
                false
            }
            else -> true
        }
    }

    fun register(email: String, password: String, name: String, navController: NavController) {
        _isLoadingState.value = true
        viewModelScope.launch {
            val user = authRepository.register(email, password)

            if (user != null) {
                val userModel = User(user.uid, email, name)
                saveUserInfo(userModel)
                navController.navigate("login")
            } else {
                _errorMessageState.value = "Erro ao registrar, tente novamente."
                _isLoadingState.value = false
            }
        }
    }

    private fun saveUserInfo(user: User) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.child(user.uid).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RegisterViewModel", "Informações do usuário salvas com sucesso no banco de dados.")
                } else {
                    Log.e("RegisterViewModel", "Erro ao salvar informações do usuário no banco de dados: ${task.exception}")
                }
            }
    }
}