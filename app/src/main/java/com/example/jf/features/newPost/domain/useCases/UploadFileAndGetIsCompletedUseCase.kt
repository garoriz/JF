package com.example.jf.features.newPost.domain.useCases

import android.net.Uri
import com.example.jf.features.myProfile.domain.repo.UserRepo
import com.example.jf.features.newPost.data.repoImpl.NewPostRepoImpl
import com.example.jf.features.newPost.domain.repo.NewPostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadFileAndGetIsCompletedUseCase @Inject constructor(
    private val repo: NewPostRepo
) {
    suspend operator fun invoke(uri: Uri, type: String): Boolean {
        return withContext(Dispatchers.Main) {
            repo.uploadFileAnfGetIsCompleted(uri, type)
        }
    }
}
