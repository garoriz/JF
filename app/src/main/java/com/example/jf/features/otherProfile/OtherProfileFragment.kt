package com.example.jf.features.otherProfile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentOtherProfileBinding
import com.example.jf.databinding.FragmentPostBinding
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.registration.domain.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private const val ARG_NAME = "uid"

class OtherProfileFragment : Fragment(R.layout.fragment_other_profile) {
    private lateinit var database: DatabaseReference
    private val storageRef = Firebase.storage.reference
    private lateinit var binding: FragmentOtherProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherProfileBinding.bind(view)
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        val uid = arguments?.getString(ARG_NAME).toString()

        database.child("users").child(uid).get().addOnSuccessListener {
            val user = it.getValue(User::class.java)
            with(binding) {
                ivAvatar.load(Uri.parse(user?.urlPhoto)) {
                    transformations(CircleCropTransformation())
                }
                tvNick.text = user?.nick
            }
        }.addOnFailureListener {
            showMessage(R.string.no_internet)
        }

    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
