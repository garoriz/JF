package com.example.jf.features.login.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        with(binding) {
            tvRegistration.setOnClickListener {
                view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (checkCredentials(email, password)) {
                    login(email, password)
                }
                ViewCompat.getWindowInsetsController(requireView())
                    ?.hide(WindowInsetsCompat.Type.ime())
            }
        }
    }

    private fun checkCredentials(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            showMessage(R.string.empty_email)
            return false
        }
        if (password.length < 6) {
            showMessage(R.string.min_6_symbols_password)
            return false
        }
        return true
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    view?.findNavController()?.apply {
                        navigate(R.id.action_loginFragment_to_navigation_main)
                        //backQueue.clear()
                    }
                } else {
                    showMessage(R.string.error)
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
