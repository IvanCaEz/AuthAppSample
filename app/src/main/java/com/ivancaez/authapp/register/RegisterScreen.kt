package com.ivancaez.authapp.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ivancaez.authapp.R
import com.ivancaez.authapp.core.presentation.generic_components.IconOutlinedTextField
import com.ivancaez.authapp.core.presentation.generic_components.PasswordOutlinedTextField
import com.ivancaez.authapp.core.presentation.navigation.LoginScreen
import com.ivancaez.authapp.ui.theme.Purple40

@Composable
fun RegisterScreen(navController: NavHostController) {
    val registerViewModel = hiltViewModel<RegisterViewModel>()
    //Username
    val userNameError by registerViewModel.userNameError.collectAsStateWithLifecycle()
    val userNameText by registerViewModel.userName.collectAsStateWithLifecycle()
    val userNameErrorText by registerViewModel.userNameErrorText.collectAsStateWithLifecycle()
    //Email
    val userEmailError by registerViewModel.userEmailError.collectAsStateWithLifecycle()
    val userEmailText by registerViewModel.userEmail.collectAsStateWithLifecycle()
    val emailErrorText by registerViewModel.emailErrorText.collectAsStateWithLifecycle()
    //Password
    val userPasswordError by registerViewModel.userPasswordError.collectAsStateWithLifecycle()
    val userPasswordText by registerViewModel.userPassword.collectAsStateWithLifecycle()
    val isPasswordVisible by registerViewModel.isPasswordVisible.collectAsStateWithLifecycle()
    val passwordErrorText by registerViewModel.passwordErrorText.collectAsStateWithLifecycle()
    //Password
    val repeatPasswordError by registerViewModel.repeatPasswordError.collectAsStateWithLifecycle()
    val repeatPasswordText by registerViewModel.userRepeatPassword.collectAsStateWithLifecycle()
    val isRepeatPasswordVisible by registerViewModel.isRepeatPasswordVisible.collectAsStateWithLifecycle()
    val repeatPasswordErrorText by registerViewModel.passwordRepeatErrorText.collectAsStateWithLifecycle()

    // Loading state
    val isLoading by registerViewModel.isLoading.collectAsStateWithLifecycle()
    // Auth
    val registerSuccess by registerViewModel.registerSuccess.collectAsStateWithLifecycle()
    val toastText by registerViewModel.toastText.collectAsStateWithLifecycle()

    val context = LocalContext.current


    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        // If the credentials are correct, it will navigate to the BookclubListScreen
        LaunchedEffect(registerSuccess){
            registerViewModel.onLoading(false)
            if (registerSuccess){
                navController.navigate(LoginScreen) {
                    popUpTo(LoginScreen) { inclusive = true }
                }
            }
        }
        // Muestra un Toast si hay error en la autenticación
        LaunchedEffect(key1 = registerViewModel.showToastChannel){
            registerViewModel.showToastChannel.collectLatest { showToast ->
                if (showToast){
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxHeight(0.40f),
            verticalArrangement = Arrangement.Center
        ){
            Image(painterResource(id = R.drawable.register), contentDescription = "Register image" )
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading){
                Box {
                    CircularProgressIndicator()
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)){
                Text("Create an account", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            // Username
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)){
                IconOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userNameText,
                    onValueChange = { registerViewModel.onTextChange("name", it) } ,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            tint = Color.Gray,
                            contentDescription = "User icon"
                        )
                    },
                    placeholderText = "Username",
                    isError = userNameError,
                    errorText = userNameErrorText
                )
            }
            // Email del usuario
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)){
                IconOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userEmailText,
                    onValueChange = { registerViewModel.onTextChange("email", it) } ,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            tint = Color.Gray,
                            contentDescription = "Email icon"
                        )
                    },
                    placeholderText = "Email",
                    isError = userEmailError,
                    errorText = emailErrorText
                )
            }
            // Contraseña
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)) {
                PasswordOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userPasswordText,
                    onValueChange = { registerViewModel.onTextChange("password", it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            tint = Color.Gray,
                            contentDescription = "Password icon"
                        ) },
                    placeholderText = "Password",
                    trailingIcon = { Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                    ) {
                        val image =
                            if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { registerViewModel.togglePasswordVisibility("password") }) {
                            Icon(
                                imageVector = image,
                                contentDescription = "Toggle password icon",
                                tint = Color.Gray
                            )
                        }
                        if (userPasswordError) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "Error Icon",
                                tint = Color.Red
                            )
                        }
                    } },
                    isPasswordVisible = isPasswordVisible,
                    isError = userPasswordError,
                    errorText = passwordErrorText
                )
            }
            // Repetir contraseña
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)) {
                PasswordOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = repeatPasswordText,
                    onValueChange = { registerViewModel.onTextChange("repeatPassword", it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            tint = Color.Gray,
                            contentDescription = "Password icon"
                        ) },
                    placeholderText = "Repeat password",
                    trailingIcon = { Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                    ) {
                        val image =
                            if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { registerViewModel.togglePasswordVisibility("repeatpassword") }) {
                            Icon(
                                imageVector = image,
                                contentDescription = "Toggle password icon",
                                tint = Color.Gray
                            )
                        }
                        if (userPasswordError) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "Error Icon",
                                tint = Color.Red
                            )
                        }
                    } },
                    isPasswordVisible = isRepeatPasswordVisible,
                    isError = repeatPasswordError,
                    errorText = repeatPasswordErrorText
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            // Botón de registro
            ElevatedButton(modifier = Modifier
                .fillMaxWidth(0.9f),
                contentPadding = PaddingValues(10.dp),
                onClick = registerViewModel::onSignUpButtonClick ,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(12.dp)
            ) {
                Text(text = "Create account", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.padding(8.dp))

            // Texto de ir a screen registro
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center) {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontStyle = MaterialTheme.typography.labelLarge.fontStyle, fontWeight = FontWeight.Bold)) {
                        append("Already have an account? ")
                    }
                    withStyle(style = SpanStyle(fontStyle = MaterialTheme.typography.labelLarge.fontStyle, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary)
                    ) {
                        append("Log in")
                    }
                }, modifier = Modifier.clickable {
                    navController.navigate(LoginScreen) {
                        popUpTo(LoginScreen) { inclusive = true }
                    }
                })
            }
        }
    }

}