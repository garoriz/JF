package com.example.jf.features.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth

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
                btnExit.visibility = View.VISIBLE
            else
                btnLogin.visibility = View.VISIBLE

            btnLogin.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_main_to_loginFragment)
            }

            btnExit.setOnClickListener {
                Firebase.auth.signOut()
                btnExit.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }
}
