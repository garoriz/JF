package com.example.jf.features.otherProfile.data.mappers


import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.newPost.domain.model.Post
import com.google.firebase.database.DataSnapshot

class UserPostMapper {
    fun map(postsData: Iterable<DataSnapshot>): MutableList<PostInList?> {
        val postList = mutableListOf<PostInList?>()
        for (postSnapshot in postsData) {
            val post = postSnapshot.getValue(Post::class.java)
            postList.add(
                PostInList(
                    postSnapshot.key,
                    post?.userId,
                    post?.heading,
                    post?.text,
                    post?.urisPhoto?.get(0),
                    post?.urisVideo?.get(0),
                )
            )
        }
        postList.reverse()
        return postList
    }
}
