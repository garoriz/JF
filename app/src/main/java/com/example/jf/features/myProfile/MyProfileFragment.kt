package com.example.jf.features.myProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {
    private lateinit var binding: FragmentMyProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyProfileBinding.bind(view)
        val currentUser = auth.currentUser

        if (currentUser != null) {
            with (binding) {
                ivAvatar.load(currentUser.photoUrl) {
                    transformations(CircleCropTransformation())
                }
                tvNick.text = currentUser.displayName
                btnEditProfile.setOnClickListener {
                    view.findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
                }
            }
        }
    }
}
