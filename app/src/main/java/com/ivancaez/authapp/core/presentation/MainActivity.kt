package com.ivancaez.authapp.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ivancaez.authapp.core.presentation.navigation.LoginScreen
import com.ivancaez.authapp.core.presentation.navigation.ProfileScreen
import com.ivancaez.authapp.core.presentation.navigation.RegisterScreen
import com.ivancaez.authapp.login.LoginScreen
import com.ivancaez.authapp.login.LoginViewModel
import com.ivancaez.authapp.profile.ProfileScreen
import com.ivancaez.authapp.register.RegisterScreen
import com.ivancaez.authapp.ui.theme.AuthAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthAppTheme {
                val navController = rememberNavController()
                val loginViewModel = hiltViewModel<LoginViewModel>()
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { paddingValues ->
                    ScreenNavigationConfiguration(
                        navController,
                        loginViewModel,
                        Modifier.padding(paddingValues),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ScreenNavigationConfiguration(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    modifier: Modifier) {

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = LoginScreen,
            modifier = modifier
        ) {
            composable<LoginScreen> {
                LoginScreen(navController, loginViewModel)
            }
            composable<RegisterScreen> {
                RegisterScreen(navController)
            }
            composable<ProfileScreen> {
                ProfileScreen(navController, loginViewModel)
            }
        }
    }
}