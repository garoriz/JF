package com.example.jf

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.asDeferred

object DbRefService {

    suspend fun updateNick(nick: String, uid: String) {
        var database: DatabaseReference = Firebase
            .database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
        database.updateChildren(
            hashMapOf(
                "/users/${uid}/nick" to nick,
            ) as Map<String, Any>
        )
    }

    suspend fun updateAvatarUri(uid: String, uri: Uri) {
        var database: DatabaseReference = Firebase
            .database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference

        database.updateChildren(
            hashMapOf(
                "/users/${uid}/urlPhoto" to uri.toString(),
            ) as Map<String, Any>
        )
    }

    suspend fun updatePosts(postLimit: Int): Iterable<DataSnapshot> {
        val database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        val posts = database.child("posts").limitToLast(postLimit)

        val task: Task<DataSnapshot> = posts.get()
        val deferredDataSnapshot: Deferred<DataSnapshot> = task.asDeferred()
        return deferredDataSnapshot.await().children
    }

    suspend fun updateOneUser(postLimit: Int, uid: String): Iterable<DataSnapshot> {
        val database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        val posts = database
            .child("users")
            .child(uid)
            .child("posts")
            .limitToLast(postLimit)
        val task: Task<DataSnapshot> = posts.get()
        val deferredDataSnapshot: Deferred<DataSnapshot> = task.asDeferred()
        return deferredDataSnapshot.await().children
    }

    suspend fun deletePost(postId: String, userId: String) {
        val database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        database
            .child("posts")
            .child(postId)
            .removeValue()
        database
            .child("users")
            .child(userId)
            .child("posts")
            .child(postId)
            .removeValue()
    }
}
