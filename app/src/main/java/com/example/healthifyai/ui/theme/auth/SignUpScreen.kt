package com.example.healthifyai.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(
    onAuthSuccess: () -> Unit,
    onGoogleSignIn: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(false) }

    fun handleAuth() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please fill in all fields."
            return
        }
        val task = if (isLogin) {
            auth.signInWithEmailAndPassword(email, password)
        } else {
            auth.createUserWithEmailAndPassword(email, password)
        }
        task.addOnCompleteListener { result ->
            if (result.isSuccessful) onAuthSuccess()
            else errorMessage = result.exception?.message ?: "Authentication failed."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email + Password Fields
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        // Error Message
        if (errorMessage.isNotBlank()) {
            Text(errorMessage, color = Color.Red)
            Spacer(Modifier.height(8.dp))
        }

        // Sign In / Sign Up Button
        Button(
            onClick = { handleAuth() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLogin) "Log In" else "Sign Up")
        }
        Spacer(Modifier.height(8.dp))

        // Toggle Mode
        TextButton(
            onClick = { isLogin = !isLogin },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLogin) "Don't have an account? Sign Up"
                else "Already have an account? Log In"
            )
        }

        // Google Sign-In Button
        Spacer(Modifier.height(24.dp))
        GoogleSignInButton(onClick = onGoogleSignIn)
    }
}
