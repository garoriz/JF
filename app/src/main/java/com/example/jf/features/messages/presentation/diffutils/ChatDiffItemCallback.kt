package com.example.jf.features.messages.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.messages.domain.Chat

class ChatDiffItemCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(
        oldItem: Chat,
        newItem: Chat
    ): Boolean = oldItem.userId == newItem.userId

    override fun areContentsTheSame(
        oldItem: Chat,
        newItem: Chat
    ): Boolean = oldItem == newItem
}
