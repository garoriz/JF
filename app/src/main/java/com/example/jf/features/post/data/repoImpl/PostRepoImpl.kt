package com.example.jf.features.post.data.repoImpl

import android.net.Uri
import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.post.domain.repo.PostRepo
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class PostRepoImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val storageRefService: StorageRefService,
): PostRepo {
    override suspend fun getPost(id: String): Post? {
        return dbRefService.getPost(id)
    }

    override suspend fun getFile(uri: String): Uri? {
        return storageRefService.getFileUrl(uri)
    }
}
