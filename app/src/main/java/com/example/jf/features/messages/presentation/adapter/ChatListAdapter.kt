package com.example.jf.features.messages.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.jf.features.messages.domain.Chat
import com.example.jf.features.messages.presentation.diffutils.ChatDiffItemCallback

class ChatListAdapter (
    private val action: (String) -> Unit,
) : ListAdapter<Chat, ChatHolder>(ChatDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatHolder = ChatHolder.create(parent, action)

    override fun onBindViewHolder(holder: ChatHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Chat?>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
