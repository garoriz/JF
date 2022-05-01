package com.example.jf.features.registration.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentRegistrationBinding
import com.example.jf.features.registration.domain.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegistrationBinding.bind(view)

        with(binding) {
            tvLogin.setOnClickListener {
                view.findNavController().navigateUp()
            }

            btnRegistration.setOnClickListener {
                val nick = etNick.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString()
                val confirmedPassword = etConfirmedPassword.text.toString()
                if (isValidCredentials(nick, email, password, confirmedPassword)) {
                    createUser(nick, email, password)
                }
                ViewCompat.getWindowInsetsController(requireView())
                    ?.hide(WindowInsetsCompat.Type.ime())
            }
        }
    }

    private fun isValidCredentials(
        nick: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (nick.isEmpty()) {
            showMessage(R.string.empty_nick)
            return false
        }
        if (email.isEmpty()) {
            showMessage(R.string.empty_email)
            return false
        }
        if (password != confirmedPassword) {
            showMessage(R.string.not_the_same_passwords)
            return false
        }
        if (password.contains(" ")) {
            showMessage(R.string.password_contains_space)
            return false
        }
        if (password.length < 6) {
            showMessage(R.string.min_6_symbols_password)
            return false
        }
        if (password.length > 30) {
            showMessage(R.string.max_30_symbols_password)
            return false
        }
        return true
    }

    private fun createUser(nick: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateProfile(nick)
                } else {
                    showMessage(R.string.no_internet)
                }
            }
    }

    private fun updateProfile(nick: String) {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = nick
            photoUri =
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/jf-forum-f415b.appspot.com/o/utils%2Findex.png?alt=media&token=fe7ea5f2-b733-432c-9899-76c87d963ec8")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userEntity = User(
                        user.displayName,
                        user.photoUrl.toString(),
                    )
                    database.child("users")
                        .child(user.uid)
                        .setValue(userEntity)
                    view?.findNavController()?.navigate(
                        R.id.action_registrationFragment_to_navigation_main
                    )
                }
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
