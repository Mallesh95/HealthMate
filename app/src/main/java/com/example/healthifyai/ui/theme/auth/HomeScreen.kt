package com.example.healthifyai.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthifyai.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    onProfileClick: () -> Unit
) {
    val user = FirebaseAuth.getInstance().currentUser
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Profile Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { onProfileClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder avatar
            Image(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user?.displayName ?: "Guest User",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = user?.email ?: "No Email",
                    style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onProfileClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = "Edit Profile"
                )
            }
        }

        // Feature Cards
        FeatureCard(title = "Daily Health Tracker") {
            // Navigate to Tracker Screen
        }
        FeatureCard(title = "Health Memory") {
            // Navigate to Health Memory Screen
        }
        FeatureCard(title = "Hospital Search") {
            // Navigate to Hospital Search Screen
        }
        FeatureCard(title = "Book Appointment") {
            // Navigate to Booking Screen
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sign Out Button
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                onSignOut()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Out")
        }
    }
}

@Composable
fun FeatureCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0D47A1)
            )
        }
    }
}
