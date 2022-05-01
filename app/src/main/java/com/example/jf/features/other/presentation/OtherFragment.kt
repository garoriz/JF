package com.example.jf.features.other.presentation

import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentOtherBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OtherFragment : Fragment(R.layout.fragment_other) {
    private lateinit var binding: FragmentOtherBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherBinding.bind(view)
        val currentUser = auth.currentUser

        with (binding) {
            if (currentUser == null) {
                btnNewPost.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                btnExit.visibility = View.GONE
                btnEditProfile.visibility = View.GONE
                btnSettings.visibility = View.GONE
            }
            btnLogin.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_other_to_loginFragment)
            }
            btnEditProfile.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_other_to_editProfileFragment)
            }
            btnNewPost.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_other_to_newPostFragment)
            }
            btnSettings.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_other_to_settingsFragment)
            }
            btnExit.setOnClickListener {
                auth.signOut()
                view.findNavController().navigate(R.id.navigation_main)
            }
        }
    }
}
