package com.example.jf.features.messages.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.jf.R
import com.example.jf.databinding.FragmentMessagesBinding
import com.example.jf.features.messages.domain.Chat
import com.example.jf.features.messages.presentation.adapter.ChatListAdapter
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

class MessagesFragment : Fragment(R.layout.fragment_messages) {
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentMessagesBinding
    private var chatListAdapter: ChatListAdapter? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMessagesBinding.bind(view)
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference
        val currentUser = auth.currentUser

        val chats = currentUser?.let {
            database
                .child("users")
                .child(it.uid)
                .child("chatsWithUnreadValue")
                .orderByKey()
        }
        chats?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chatsList = mutableListOf<Chat?>()
                for (chatSnapshot in dataSnapshot.children) {
                    val userId = chatSnapshot.key
                    chatsList.add(Chat(
                        currentUser.uid,
                        userId,
                        chatSnapshot.getValue(Boolean::class.java)
                    ))
                }
                chatListAdapter = ChatListAdapter {
                    getAllChat(it)
                    chatListAdapter?.submitList(chatsList)
                }

                val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

                binding.chats.run {
                    adapter = chatListAdapter
                    addItemDecoration(decorator)
                }

                chatListAdapter?.submitList(chatsList)
                chats.removeEventListener(this)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showMessage(R.string.no_internet)
            }
        })
    }

    private fun getAllChat(userId: String) {
        view?.findNavController()
            ?.navigate(
                R.id.action_messagesFragment_to_chatFragment,
                bundleOf(ARG_NAME_USER_ID to userId)
            )
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
