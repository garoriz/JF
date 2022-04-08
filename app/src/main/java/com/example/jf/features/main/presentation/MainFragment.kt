package com.example.jf.features.main.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    private val storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        val currentUser = auth.currentUser

        with(binding) {
            if (currentUser != null)
                ivAvatar.setOnClickListener {
                    auth.signOut()
                }
            else
                ivAvatar.setOnClickListener {
                    view.findNavController().navigate(R.id.action_navigation_main_to_loginFragment)
                }
        }
    }
}
