package com.ivancaez.authapp.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivancaez.authapp.core.data.remote.dto.auth.AuthRequest
import com.ivancaez.authapp.core.data.remote.dto.user.UserDto
import com.ivancaez.authapp.core.domain.repository.AuthRepository
import com.ivancaez.authapp.core.domain.repository.UserRepository
import com.ivancaez.authapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
    ): ViewModel() {
    // EMAIL
    // Text
    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()
    //Error
    private val _userEmailError = MutableStateFlow(false)
    val userEmailError = _userEmailError.asStateFlow()

    // PASSWORD
    // Text
    private val _userPassword = MutableStateFlow("")
    val userPassword = _userPassword.asStateFlow()
    // Visibility
    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()
    // Error
    private val _userPasswordError = MutableStateFlow(false)
    val userPasswordError = _userPasswordError.asStateFlow()

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    // ERRORES
    private val _emailErrorText = MutableStateFlow("")
    val emailErrorText = _emailErrorText.asStateFlow()
    private val _passwordErrorText = MutableStateFlow("")
    val passwordErrorText = _passwordErrorText.asStateFlow()


    fun onLoginButtonClick() {

        // Variables to obtain the values of the input
        val userEmail = _userEmail.value
        val userPassword = _userPassword.value

        // Check if there are empty fields
        if (!onError("email", userEmail.isEmpty()) &&
            !onError("password",userPassword.isEmpty())) {

            //Check the Email format
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                onError("email", isError = true)
                onErrorTextChange("email", "The email address is invalid")
                return
            }
            login(AuthRequest(userEmail, userPassword))
        } else {
            onErrorTextChange("email", "Email cannot be empty")
            onErrorTextChange("password", "Password cannot be empty")
            return
        }
    }

    fun onTextChange(which: String, text: String){
        when(which){
            "email" -> _userEmail.value = text
            "password" -> _userPassword.value = text
        }
    }
    private fun onError(which: String, isError: Boolean): Boolean {
        when(which){
            "email" -> _userEmailError.value = isError
            "password" -> _userPasswordError.value = isError
        }
        return isError
    }
    private fun onErrorTextChange(which: String, text: String){
        when(which){
            "email" -> _emailErrorText.value = text
            "password" -> _passwordErrorText.value = text
        }
    }

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow("")
    val refreshToken = _refreshToken.asStateFlow()

    private val _showToastChannel = Channel<Boolean>()
    val showToastChannel = _showToastChannel.receiveAsFlow()

    private val _toastText = MutableStateFlow("")
    val toastText = _toastText.asStateFlow()

    private fun login(authRequest: AuthRequest){
        viewModelScope.launch{
            val result = authRepository.authenticate(authRequest)

            when (result) {
                is Resource.Success -> {
                    result.data?.let { authResult ->
                        _accessToken.value = authResult.accessToken
                        _refreshToken.value = authResult.refreshToken
                        getUserByEmail()
                    }
                }
                is Resource.Error -> {
                    when (result.message ) {
                        "Not Found" -> {
                            _toastText.value = "This email is not registered"
                        }
                        "Unauthorized" -> {
                            _toastText.value = "Wrong credentials"
                        }
                        "Forbidden" -> {
                            _toastText.value = "You are not allowed to access this resource"
                        }
                        else -> {
                            _toastText.value = result.message!!+" Unknown Error"
                        }
                    }
                    _showToastChannel.send(true)
                }

                else -> {}
            }

        }
    }
    // Error
    private val _userIsLogged = MutableStateFlow(false)
    val userIsLogged = _userIsLogged.asStateFlow()

    private val _user = MutableStateFlow<UserDto?>(null)
    val user = _user.asStateFlow()

    private fun getUserByEmail(){
        viewModelScope.launch{
            val result = userRepository.getUserByEmail(userEmail.value, accessToken.value)
            when (result) {
                is Resource.Success -> {
                    result.data?.let { user ->
                        _toastText.value = "Welcome ${user.username}"
                        _showToastChannel.send(true)
                        _userIsLogged.value = true
                        _user.value = user
                    }
                }
                is Resource.Error -> {
                    when (result.message ) {
                        "Not Found" -> {
                            _toastText.value = "This email is not registered"
                        }
                        "Unauthorized" -> {
                            _toastText.value = "Wrong credentials"
                        }
                        "Forbidden" -> {
                            _toastText.value = "You are not allowed to access this resource"
                        }
                        else -> {
                            _toastText.value = result.message!!+" Unknown Error"
                        }
                    }
                    _showToastChannel.send(true)
                }

                else -> {}
            }

        }
    }

}