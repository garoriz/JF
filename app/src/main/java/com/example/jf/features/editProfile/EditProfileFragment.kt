package com.example.jf.features.editProfile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentEditProfileBinding
import com.example.jf.databinding.FragmentOtherBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private val storageRef = Firebase.storage.reference
    private var currentUser: FirebaseUser? = null

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val path = "avatars/${it.lastPathSegment}"
            val uploadTask = storageRef.child(path).putFile(uri)
            showMessage(R.string.loading_file)

            uploadTask.addOnFailureListener {
                showMessage(R.string.no_internet)
            }.addOnSuccessListener {
                storageRef.child(path).downloadUrl.addOnSuccessListener {
                    val profileUpdates = userProfileChangeRequest {
                        photoUri = it
                    }

                    currentUser!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                binding.ivAvatar.load(it) {
                                    transformations(CircleCropTransformation())
                                }
                            }
                        }
                }.addOnFailureListener {
                    showMessage(R.string.error_loading_media)
                }
            }
        }

    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            auth = Firebase.auth
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding = FragmentEditProfileBinding.bind(view)
            currentUser = auth.currentUser

            if (currentUser != null) {
                with(binding) {
                    ivAvatar.load(currentUser?.photoUrl) {
                        transformations(CircleCropTransformation())
                    }
                    etNick.setText(currentUser?.displayName)
                    btnChangePhoto.setOnClickListener {
                        selectImageFromGallery()
                    }
                    btnSave.setOnClickListener {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = etNick.text.toString()
                        }

                        currentUser!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showMessage(R.string.nick_saved_successfully)
                                }
                            }
                        ViewCompat.getWindowInsetsController(requireView())
                            ?.hide(WindowInsetsCompat.Type.ime())
                    }
                }
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

        private fun showMessage(stringId: Int) {
            Snackbar.make(
                binding.root,
                stringId,
                Snackbar.LENGTH_LONG
            ).show()
        }
}
