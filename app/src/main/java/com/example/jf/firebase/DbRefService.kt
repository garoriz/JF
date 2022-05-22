package com.example.jf.firebase

import android.net.Uri
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.otherProfile.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

private const val DEFAULT_AVATAR = "https://firebasestorage.googleapis.com/v0/b/jf-forum-f415b.appspot.com/o/utils%2Findex.png?alt=media&token=fe7ea5f2-b733-432c-9899-76c87d963ec8"

class DbRefService {

    val database: DatabaseReference = Firebase
        .database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
        .reference

    suspend fun updateNick(nick: String, uid: String) {
        database.updateChildren(
            hashMapOf(
                "/users/${uid}/nick" to nick,
            ) as Map<String, Any>
        )
    }

    suspend fun updateAvatarUri(uid: String, uri: Uri) {
        database.updateChildren(
            hashMapOf(
                "/users/${uid}/urlPhoto" to uri.toString(),
            ) as Map<String, Any>
        )
    }

    suspend fun updatePosts(postLimit: Int): Iterable<DataSnapshot> {
        val posts = database.child("posts").limitToLast(postLimit)

        val task: Task<DataSnapshot> = posts.get()
        val deferredDataSnapshot: Deferred<DataSnapshot> = task.asDeferred()
        return deferredDataSnapshot.await().children
    }

    suspend fun updatePostsOfOneUser(postLimit: Int, uid: String): Iterable<DataSnapshot> {
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

    suspend fun addPost(post: Post, uid: String) {
        val key = database.child("posts")
            .push().key
        if (key != null) {
            database.child("posts")
                .child(key)
                .setValue(post)
            database.child("users")
                .child(uid)
                .child("posts")
                .child(key)
                .setValue(post)
        }
    }

    suspend fun getUser(uid: String): User? {
        return database.child("users").child(uid).get().await().getValue(User::class.java)
    }

    suspend fun getPost(id: String): Post? {
        return database.child("posts").child(id).get().await().getValue(Post::class.java)
    }

    suspend fun addUser(nick: String, uid: String) {
        val userEntity = User(
            nick,
            DEFAULT_AVATAR,
        )
        database.child("users")
            .child(uid)
            .setValue(userEntity)
    }

    companion object {
        var dbRefService = DbRefService()

        fun getInstance(): DbRefService {
            return dbRefService
        }
    }
}
