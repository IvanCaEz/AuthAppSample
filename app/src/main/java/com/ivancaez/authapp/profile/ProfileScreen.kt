package com.ivancaez.authapp.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ivancaez.authapp.login.LoginViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val user by loginViewModel.user.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = user!!.username, style = MaterialTheme.typography.headlineMedium)
        Text(text = user!!.email, style = MaterialTheme.typography.headlineSmall)

    }

}