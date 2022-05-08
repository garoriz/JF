package com.example.jf.features.messages.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.ItemChatBinding
import com.example.jf.databinding.ItemPostBinding
import com.example.jf.features.chat.domain.Message
import com.example.jf.features.chat.presentation.adapter.MessageListAdapter
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.adapter.PostHolder
import com.example.jf.features.messages.domain.Chat
import com.example.jf.features.registration.domain.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatHolder(
    private val binding: ItemChatBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val database: DatabaseReference = Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
        .reference
    private var chat: Chat? = null

    init {
        itemView.setOnClickListener {
            chat?.otherUserId?.also(action)
        }
    }

    fun bind(item: Chat) {
        this.chat = item
        with(binding) {
            item.otherUserId?.let { it ->
                database.child("users").child(it).get().addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    ivAvatar.load(user?.urlPhoto) {
                        transformations(CircleCropTransformation())
                    }
                    tvNick.text = user?.nick
                    if (item.isUnread == true) {
                        badge.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (String) -> Unit
        ) = ChatHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
