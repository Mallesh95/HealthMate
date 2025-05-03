package com.example.healthifyai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import com.example.healthifyai.ui.theme.navigation.MainNavigation
import com.example.healthifyai.ui.auth.SignUpScreen
import com.example.healthifyai.ui.theme.HealthifyAITheme
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*

class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        // 1) Configure Google Sign‑In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 2) Track sign‑in state
        val isSignedIn = mutableStateOf(auth.currentUser != null)

        // 3) Launcher for Google Sign‑In intent
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount = task.getResult(Exception::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            isSignedIn.value = true
                        }
                    }
            } catch (e: Exception) {
                // handle or log error
            }
        }

        // 4) Compose UI
        setContent {
            HealthifyAITheme {
                if (isSignedIn.value) {
                    MainNavigation(
                        onSignOut = {
                            // sign out both Firebase and Google
                            auth.signOut()
                            googleSignInClient.signOut()
                            isSignedIn.value = false
                        }
                    )
                } else {
                    SignUpScreen(
                        onAuthSuccess = { isSignedIn.value = true },
                        onGoogleSignIn = { launcher.launch(googleSignInClient.signInIntent) }
                    )
                }
            }
        }
    }
}
