package com.example.jf.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val DEFAULT_AVATAR = "https://firebasestorage.googleapis.com/v0/b/jf-forum-f415b.appspot.com/o/utils%2Findex.png?alt=media&token=fe7ea5f2-b733-432c-9899-76c87d963ec8"

class FirebaseAuthService {

    fun getCurrentUser(): FirebaseUser? {
        val auth = Firebase.auth
        return auth.currentUser
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

    fun signOut() {
        val auth = Firebase.auth
        auth.signOut()
    }

    suspend fun createUserAndGetUid(email: String, password: String, nick: String): String? {
        val auth = Firebase.auth
        var uid: String? = null
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    uid = user?.uid
                    val profileUpdates = userProfileChangeRequest {
                        displayName = nick
                        photoUri =
                            Uri.parse(DEFAULT_AVATAR)
                    }

                    user!!.updateProfile(profileUpdates)
                }
            }.await()
        return uid
    }

    fun changePassword(password: String) {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUser?.updatePassword(password)
    }

    companion object {
        var firebaseAuthService = FirebaseAuthService()

        fun getInstance(): FirebaseAuthService {
            return firebaseAuthService
        }
    }
}
