package com.example.jf.features.newPost.data.repoImpl

import android.net.Uri
import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.newPost.domain.repo.NewPostRepo
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject


class NewPostRepoImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val storageRefService: StorageRefService,
): NewPostRepo {
    override suspend fun addPost(post: Post, uid: String) {
        dbRefService.addPost(post, uid)
    }

    override suspend fun uploadFileAnfGetIsCompleted(uri: Uri, type: String): Boolean {
        return storageRefService.uploadFileAndGetIsCompleted(uri, type)
    }
}
