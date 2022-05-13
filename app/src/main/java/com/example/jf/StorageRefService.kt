package com.example.jf

import android.net.Uri
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

object StorageRefService {
    suspend fun uploadAvatarAndIsCompleted(uri: Uri) : Boolean {
        var isCompleted = false
        val storageRef = Firebase.storage.reference

        val path = "avatars/${uri.lastPathSegment}"
        val uploadTask = storageRef.child(path).putFile(uri)

        uploadTask.addOnFailureListener {
            return@addOnFailureListener
        }.addOnSuccessListener {
            isCompleted = true
        }.await()
        return isCompleted
    }

    suspend fun getDownloadAvatarUri(uri: Uri): Uri? {
        var downloadUri: Uri? = null
        val storageRef = Firebase.storage.reference
        val path = "avatars/${uri.lastPathSegment}"

        storageRef.child(path).downloadUrl.addOnSuccessListener {
            downloadUri = it
        }.addOnFailureListener {
            return@addOnFailureListener
        }.await()
        return downloadUri
    }
}
