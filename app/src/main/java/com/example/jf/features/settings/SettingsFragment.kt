package com.example.jf.features.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentEditProfileBinding
import com.example.jf.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        currentUser = auth.currentUser

        if (currentUser != null) {
            with(binding) {
                btnChangePassword.setOnClickListener {
                    if (isValidCredentials(
                            etPassword.text.toString(),
                            etConfirmedPassword.text.toString())
                    ) {
                        currentUser?.updatePassword(etPassword.text.toString())
                        showMessage(R.string.change_password_successfully)
                        view.findNavController().navigateUp()
                    }
                    ViewCompat.getWindowInsetsController(requireView())
                        ?.hide(WindowInsetsCompat.Type.ime())
                }
            }
        }
    }

    private fun isValidCredentials(password: String, confirmedPassword: String): Boolean {
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

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
