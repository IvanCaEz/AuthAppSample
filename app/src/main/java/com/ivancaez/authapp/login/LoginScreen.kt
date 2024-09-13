package com.ivancaez.authapp.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ivancaez.authapp.R
import com.ivancaez.authapp.core.presentation.generic_components.IconOutlinedTextField
import com.ivancaez.authapp.core.presentation.generic_components.PasswordOutlinedTextField
import com.ivancaez.authapp.core.presentation.navigation.ProfileScreen
import com.ivancaez.authapp.core.presentation.navigation.RegisterScreen

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    //Email
    val userEmailError by loginViewModel.userEmailError.collectAsStateWithLifecycle()
    val userEmailText by loginViewModel.userEmail.collectAsStateWithLifecycle()
    val userEmailErrorText by loginViewModel.emailErrorText.collectAsStateWithLifecycle()
    //Password
    val userPasswordError by loginViewModel.userPasswordError.collectAsStateWithLifecycle()
    val userPasswordText by loginViewModel.userPassword.collectAsStateWithLifecycle()
    val isPasswordVisible by loginViewModel.isPasswordVisible.collectAsStateWithLifecycle()
    val passwordErrorText by loginViewModel.passwordErrorText.collectAsStateWithLifecycle()

    val toastText by loginViewModel.toastText.collectAsStateWithLifecycle()
    val userIsLogged by loginViewModel.userIsLogged.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = loginViewModel.showToastChannel){
        loginViewModel.showToastChannel.collectLatest { showToast ->
            if (showToast){
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = userIsLogged){
        if (userIsLogged){
            navController.navigate(ProfileScreen){
                popUpTo(ProfileScreen){ inclusive = true }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier
            .fillMaxHeight(0.40f),
            verticalArrangement = Arrangement.Center
        ){
            Image(painterResource(id = R.drawable.login), contentDescription = "Login image" )
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)){
                Text("Log In",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(modifier = Modifier.padding(8.dp))

            // Email del usuario
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)){
                IconOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userEmailText,
                    onValueChange = { loginViewModel.onTextChange("email", it) } ,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            tint = Color.Gray,
                            contentDescription = "Email icon"
                        )
                    },
                    placeholderText = "Email",
                    isError = userEmailError,
                    errorText = userEmailErrorText
                )
            }
            // Contraseña
            Row(modifier = Modifier
                .fillMaxWidth(0.9f)) {
                PasswordOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userPasswordText,
                    onValueChange = { loginViewModel.onTextChange("password", it) },
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
                        IconButton(onClick = { loginViewModel.togglePasswordVisibility() }) {
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
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.End) {
                Text(text = "Forgot password?", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            // Botón de inicio de sesión
            ElevatedButton(modifier = Modifier
                .fillMaxWidth(0.9f),
                contentPadding = PaddingValues(10.dp),
                onClick = loginViewModel::onLoginButtonClick ,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(12.dp)
            ) {
                Text(text = "Log In", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.padding(8.dp))

            // Texto de ir a screen registro
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center) {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontStyle = MaterialTheme.typography.labelLarge.fontStyle, fontWeight = FontWeight.Bold)) {
                        append("Don't have an account? ")
                    }
                    withStyle(style = SpanStyle(fontStyle = MaterialTheme.typography.labelLarge.fontStyle, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary)) {
                        append("Sign Up")
                    }
                }, modifier = Modifier.clickable {
                    navController.navigate(RegisterScreen) {
                        popUpTo(RegisterScreen) { inclusive = true }
                    }
                })
            }
        }
    }


}
