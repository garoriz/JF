package com.example.jf

import android.net.Uri
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    suspend fun getCurrentUser(): FirebaseUser? {
        val auth = Firebase.auth
        return auth.currentUser
    }

    suspend fun updateNick(nick: String): Boolean {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        var isCompleted = false

        val profileUpdates = userProfileChangeRequest {
            displayName = nick
        }

        currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()
        return isCompleted
    }

    suspend fun updateAvatarUriAndGetIsCompleted(uri: Uri): Boolean {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        var isCompleted = false


        val profileUpdates = userProfileChangeRequest {
            photoUri = uri
        }

        currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()
        return isCompleted
    }

    suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean {
        val auth = Firebase.auth
        var isCompleted = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()
        return isCompleted
    }
}
