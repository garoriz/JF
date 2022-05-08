package com.example.jf.features.chat.presentation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.PopupMenu
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentChatBinding
import com.example.jf.features.chat.domain.Message
import com.example.jf.features.chat.presentation.adapter.MessageListAdapter
import com.example.jf.features.registration.domain.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val ARG_NAME_USER_ID = "uid"

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentChatBinding
    private lateinit var auth: FirebaseAuth
    private var messageListAdapter: MessageListAdapter? = null
    private lateinit var popupMenu: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatBinding.bind(view)
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference
        val currentUser = auth.currentUser

        val otherProfileId =
            arguments?.getString(ARG_NAME_USER_ID).toString()

        popupMenu = PopupMenu(requireContext(), binding.tvNick)
        popupMenu.inflate(R.menu.popup_menu_in_chat)

        if (currentUser != null) {
            val currentUserId = currentUser.uid
            val childUpdates = hashMapOf<String, Any>(
                "/users/$currentUserId/chatsWithUnreadValue/$otherProfileId" to false,
            )

            database.updateChildren(childUpdates)
        }

        with(binding) {
            database.child("users").child(otherProfileId).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                ivAvatar.load(Uri.parse(user?.urlPhoto)) {
                    transformations(CircleCropTransformation())
                }
                tvNick.text = user?.nick
            }.addOnFailureListener {
                showMessage(R.string.no_internet)
            }
            val messages = currentUser?.let {
                database
                    .child("users")
                    .child(it.uid)
                    .child("messages")
                    .child(otherProfileId)
                    .orderByKey()
            }
            messages?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val messageList = mutableListOf<Message?>()
                    for (messageSnapshot in dataSnapshot.children) {
                        messageList.add(messageSnapshot.getValue(Message::class.java))
                    }
                    messageListAdapter = MessageListAdapter {
                        deleteMessage(it, currentUser.uid, otherProfileId)
                        messageListAdapter?.submitList(messageList)
                    }

                    binding.messages.run {
                        adapter = messageListAdapter
                    }

                    messageListAdapter?.submitList(messageList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showMessage(R.string.no_internet)
                }
            })
            ibSend.setOnClickListener {
                val text = etMessage.text.toString()
                if (text == "")
                    showMessage(R.string.message_should_not_empty)
                else {
                    if (currentUser != null) {
                        val userId = currentUser.uid
                        etMessage.text.clear()
                        val key = database
                            .child("users")
                            .child(userId)
                            .child("messages")
                            .child(otherProfileId)
                            .push().key
                        val message = Message(key, userId, text)
                        if (key != null) {
                            database
                                .child("users")
                                .child(userId)
                                .child("messages")
                                .child(otherProfileId)
                                .child(key)
                                .setValue(message)
                            database
                                .child("users")
                                .child(otherProfileId)
                                .child("messages")
                                .child(userId)
                                .child(key)
                                .setValue(message)
                            database
                                .child("users")
                                .child(userId)
                                .child("chatsWithUnreadValue")
                                .child(otherProfileId)
                                .setValue(false)
                            database
                                .child("users")
                                .child(otherProfileId)
                                .child("chatsWithUnreadValue")
                                .child(userId)
                                .setValue(true)
                            database
                                .child("users")
                                .child(otherProfileId)
                                .child("isUnreadChat")
                                .setValue(true)
                        }
                    }
                }
            }
        }
    }

    private fun deleteMessage(message: Message, userId: String, otherUserId: String) {
        val messageId = message.id
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_message -> {
                    if (messageId != null) {
                        database
                            .child("users")
                            .child(userId)
                            .child("messages")
                            .child(otherUserId)
                            .child(messageId)
                            .removeValue()
                        database
                            .child("users")
                            .child(otherUserId)
                            .child("messages")
                            .child(userId)
                            .child(messageId)
                            .removeValue()
                        database
                            .child("users")
                            .child(otherUserId)
                            .child("unreadChats")
                            .child(messageId)
                            .removeValue()
                    }
                }
            }
            false
        }
        if (userId == message.userId)
            popupMenu.show()
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
