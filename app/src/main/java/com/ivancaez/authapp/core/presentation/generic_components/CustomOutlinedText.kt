package com.ivancaez.authapp.core.presentation.generic_components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun NormalOutlinedTextField(
    modifier: Modifier, value: String, onValueChange: (String) -> Unit,
    placeholderText: String, isError: Boolean, errorText: String) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange =  onValueChange,
        placeholder = {
            Text(text = placeholderText, color = Color.Gray)
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Gray,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorText,
                    color = Color.Red
                )
            }
        }
    )
}

@Composable
fun IconOutlinedTextField(
    modifier: Modifier, value: String, onValueChange: (String) -> Unit, leadingIcon: @Composable () -> Unit,
    placeholderText: String, isError: Boolean, errorText: String) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange =  onValueChange,
        leadingIcon = leadingIcon,
        placeholder = {
            Text(text = placeholderText, color = Color.Gray)
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Gray,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorText,
                    color = Color.Red
                )
            }
        }
    )
}

@Composable
fun MultilineOutlinedTextField(
    modifier: Modifier, value: String, onValueChange: (String) -> Unit,
    placeholderText: String, isError: Boolean, errorText: String) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange =  onValueChange,
        placeholder = {
            Text(text = placeholderText, color = Color.Gray)
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Gray,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = false,
        maxLines = 7,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorText,
                    color = Color.Red
                )
            }
        }
    )
}

@Composable
fun PasswordOutlinedTextField(
    modifier: Modifier, value: String, onValueChange: (String) -> Unit, leadingIcon: @Composable () -> Unit,
    placeholderText: String, trailingIcon: @Composable () -> Unit,
    isPasswordVisible: Boolean, isError: Boolean, errorText: String){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange =  onValueChange ,
        leadingIcon = leadingIcon,
        placeholder = {
            Text(text = placeholderText, color = Color.Gray)
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Gray,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        trailingIcon = { trailingIcon() },
        visualTransformation =
        if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorText,
                    color = Color.Red
                )
            }
        }
    )
}