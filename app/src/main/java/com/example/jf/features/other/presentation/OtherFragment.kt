package com.example.jf.features.other.presentation

import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentOtherBinding
import com.example.jf.features.registration.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OtherFragment : Fragment(R.layout.fragment_other) {
    private lateinit var binding: FragmentOtherBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherBinding.bind(view)
        val currentUser = auth.currentUser
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        with(binding) {
            if (currentUser == null) {
                btnNewPost.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                btnExit.visibility = View.GONE
                btnEditProfile.visibility = View.GONE
                btnSettings.visibility = View.GONE
                btnMessages.visibility = View.GONE
            } else {
                database
                    .child("users")
                    .child(currentUser.uid)
                    .child("isUnreadChat")
                    .get()
                    .addOnSuccessListener {
                        val isUnreadChat = it.getValue(Boolean::class.java)
                        if (isUnreadChat == true)
                            badge.visibility = View.VISIBLE
                    }
            }
            btnMessages.setOnClickListener {
                if (currentUser != null) {
                    database
                        .child("users")
                        .child(currentUser.uid)
                        .child("isUnreadChat")
                        .removeValue()
                }
                view.findNavController()
                    .navigate(R.id.action_navigation_other_to_messagesFragment)
            }
            btnLogin.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_other_to_loginFragment)
            }
            btnEditProfile.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_navigation_other_to_editProfileFragment)
            }
            btnNewPost.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_navigation_other_to_newPostFragment)
            }
            btnSettings.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_navigation_other_to_settingsFragment)
            }
            btnExit.setOnClickListener {
                auth.signOut()
                view.findNavController().navigate(R.id.navigation_main)
            }
        }
    }
}
