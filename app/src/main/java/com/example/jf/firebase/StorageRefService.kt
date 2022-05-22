package com.example.jf.firebase

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageRefService {
    private val storageRef = Firebase.storage.reference

    suspend fun uploadAvatarAndIsCompleted(uri: Uri) : Boolean {
        var isCompleted = false
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
        val path = "avatars/${uri.lastPathSegment}"

        storageRef.child(path).downloadUrl.addOnSuccessListener {
            downloadUri = it
        }.addOnFailureListener {
            return@addOnFailureListener
        }.await()
        return downloadUri
    }

    suspend fun uploadFileAndGetIsCompleted(uri: Uri, type: String): Boolean {
        var isCompleted = false

        val path = "${type}/${uri.lastPathSegment}"
        val uploadTask = storageRef.child(path).putFile(uri)

        uploadTask.addOnFailureListener {
            isCompleted = false
        }.addOnSuccessListener {
            isCompleted = true
        }.await()
        return isCompleted
    }

    suspend fun getFileUrl(uri: String): Uri? {
        var downloadUrl: Uri? = null
        storageRef.child(uri).downloadUrl.addOnSuccessListener {
            downloadUrl = it
        }.addOnFailureListener {
            return@addOnFailureListener
        }.await()
        return downloadUrl
    }

    companion object {
        var storageRefService = StorageRefService()

        fun getInstance(): StorageRefService {
            return storageRefService
        }
    }
}
