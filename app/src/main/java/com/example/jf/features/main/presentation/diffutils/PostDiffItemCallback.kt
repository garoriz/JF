package com.example.jf.features.main.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.jf.features.main.domain.model.PostInList

class PostDiffItemCallback : DiffUtil.ItemCallback<PostInList>() {
    override fun areItemsTheSame(
        oldItem: PostInList,
        newItem: PostInList
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PostInList,
        newItem: PostInList
    ): Boolean = oldItem == newItem
}
