package com.example.healthifyai.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit
) {
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "Guest User"
    val email = user?.email ?: "No Email"
    val photoUrl = user?.photoUrl

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (photoUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(photoUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        } else {
            // Placeholder circle
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = displayName,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = email,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onSignOut() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Out")
        }
    }
}
