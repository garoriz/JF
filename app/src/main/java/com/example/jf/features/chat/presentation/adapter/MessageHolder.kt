package com.example.jf.features.chat.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.databinding.ItemMessageBinding
import com.example.jf.databinding.ItemPostBinding
import com.example.jf.features.chat.domain.Message
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.adapter.PostHolder
import com.example.jf.features.registration.domain.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageHolder(
    private val binding: ItemMessageBinding,
    private val action: (Message) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val database: DatabaseReference = Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
        .reference
    private var message: Message? = null

    init {
        itemView.setOnLongClickListener {
            message?.also(action)
            return@setOnLongClickListener true
        }
    }

    fun bind(item: Message) {
        this.message = item
        with(binding) {
            item.userId?.let { it ->
                database.child("users").child(it).get().addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    ivAvatar.load(user?.urlPhoto) {
                        transformations(CircleCropTransformation())
                    }
                }
            }
            tvMessage.text = item.text
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (Message) -> Unit
        ) = MessageHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
