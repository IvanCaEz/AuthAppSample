package com.ivancaez.authapp.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivancaez.authapp.core.data.remote.dto.user.Role
import com.ivancaez.authapp.core.data.remote.dto.user.UserDto
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
class RegisterViewModel @Inject constructor (
    private val userRepository: UserRepository): ViewModel() {
    // USERNAME
    // Text
    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()
    // Error
    private val _userNameError = MutableStateFlow(false)
    val userNameError = _userNameError.asStateFlow()

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

    // REPEAT PASSWORD
    // Text
    private val _userRepeatPassword = MutableStateFlow("")
    val userRepeatPassword = _userRepeatPassword.asStateFlow()
    // Visibility
    private val _isRepeatPasswordVisible = MutableStateFlow(false)
    val isRepeatPasswordVisible = _isRepeatPasswordVisible.asStateFlow()
    // Error
    private val _repeatPasswordError = MutableStateFlow(false)
    val repeatPasswordError = _repeatPasswordError.asStateFlow()

    private val _showToastChannel = Channel<Boolean>()
    val showToastChannel = _showToastChannel.receiveAsFlow()

    private val _toastText = MutableStateFlow("")
    val toastText = _toastText.asStateFlow()

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    /**
     * Function to toggle the visibility of the password
     * @param which: String to determine which password to toggle
     *
     * Example: "password" or "repeatPassword"
     */
    fun togglePasswordVisibility(which: String) {
        if (which == "password") _isPasswordVisible.value = !_isPasswordVisible.value
        else _isRepeatPasswordVisible.value = !_isRepeatPasswordVisible.value
    }

    private val _userCanSignUp = MutableStateFlow(false)
    val userCanSignUp = _userCanSignUp.asStateFlow()

    // ERRORES
    private val _userNameErrorText = MutableStateFlow("")
    val userNameErrorText = _userNameErrorText.asStateFlow()
    private val _emailErrorText = MutableStateFlow("")
    val emailErrorText = _emailErrorText.asStateFlow()
    private val _passwordErrorText = MutableStateFlow("")
    val passwordErrorText = _passwordErrorText.asStateFlow()
    private val _passwordRepeatErrorText = MutableStateFlow("")
    val passwordRepeatErrorText = _passwordRepeatErrorText.asStateFlow()

    fun onSignUpButtonClick() {
        // Variables to obtain the values of the input
        val userName = _userName.value
        val userEmail = _userEmail.value
        val userPassword = _userPassword.value
        val repeatPassword = _userRepeatPassword.value

        // Check if there are empty fields
        if (!onError("email", userEmail.isEmpty()) &&
            !onError("password",userPassword.isEmpty()) &&
            !onError("name", userName.isEmpty()) &&
            !onError("repeatPassword", repeatPassword.isEmpty())) {

            // Check if the passwords match
            if (userPassword != repeatPassword) {
                onErrorTextChange("repeatPassword", "Passwords do not match")
                onError("repeatPassword", isError = true)
                onError("password", isError = true)
                return
            }

            //Check the Email format
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                onError("email", isError = true)
                onErrorTextChange("email", "The email address is invalid")
                return
            }

            //Check the password format
            if (userPassword.length < 8) {
                onError("password", isError = true)
                onErrorTextChange("password", "Password must be at least 8 characters long")
                return
            }

            val newUser = UserDto(
                id = null,
                userName,
                userEmail,
                "uploads\\default.jpg",
                userPassword,
                Role.USER
            )
            register(newUser)
            _userCanSignUp.value = true
            _isLoading.value = true
        } else {
            onErrorTextChange("email", "The email address is invalid")
            onErrorTextChange("password", "Password cannot be empty")
            onErrorTextChange("repeatPassword", "Passwords do not match")
            onErrorTextChange("name", "Username cannot be empty")
            return
        }

    }

    private fun register(newUser: UserDto){
        viewModelScope.launch{
            val result = userRepository.register(newUser)

            when (result) {
                is Resource.Success -> {
                    result.data?.let { authResult ->
                        _toastText.value = "Account created, please log in"
                        _showToastChannel.send(true)
                        _registerSuccess.value = true
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


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun onLoading(isLoading: Boolean){
        _isLoading.value = isLoading
    }

    fun onTextChange(which: String, text: String){
        when(which){
            "name" -> _userName.value = text
            "email" -> _userEmail.value = text
            "password" -> _userPassword.value = text
            "repeatPassword" -> _userRepeatPassword.value = text
        }
    }

    private fun onError(which: String, isError: Boolean): Boolean {
        when(which){
            "name" -> _userNameError.value = isError
            "email" -> _userEmailError.value = isError
            "password" -> _userPasswordError.value = isError
            "repeatPassword" -> _repeatPasswordError.value = isError
        }
        return isError
    }

    private fun onErrorTextChange(which: String, text: String){
        when(which){
            "name" -> _userNameErrorText.value = text
            "email" -> _emailErrorText.value = text
            "password" -> _passwordErrorText.value = text
            "repeatPassword" -> _passwordRepeatErrorText.value = text
        }
    }

}