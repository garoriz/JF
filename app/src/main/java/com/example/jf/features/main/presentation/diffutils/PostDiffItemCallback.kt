package com.example.jf.features.main.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.jf.features.main.domain.model.Post

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean = oldItem.text == newItem.text

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean = oldItem == newItem
}
