package com.example.jf.features.chat.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.jf.features.chat.domain.Message
import com.example.jf.features.chat.presentation.diffutils.MessageDiffItemCallback
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.adapter.PostHolder
import com.example.jf.features.main.presentation.diffutils.PostDiffItemCallback

class MessageListAdapter(
    private val action: (Message) -> Unit,
) : ListAdapter<Message, MessageHolder>(MessageDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageHolder = MessageHolder.create(parent, action)

    override fun onBindViewHolder(holder: MessageHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Message?>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
