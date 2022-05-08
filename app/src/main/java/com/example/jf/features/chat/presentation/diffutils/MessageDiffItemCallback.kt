package com.example.jf.features.chat.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.jf.features.chat.domain.Message
import com.example.jf.features.main.domain.model.PostInList

class MessageDiffItemCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean = oldItem == newItem
}
