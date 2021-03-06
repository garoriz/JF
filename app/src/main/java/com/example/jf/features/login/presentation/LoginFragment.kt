package com.example.jf.features.login.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentLoginBinding
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        initObservers()

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

    private fun initObservers() {
        viewModel.isCompleted.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    view?.findNavController()?.apply {
                        navigate(R.id.action_loginFragment_to_navigation_main)
                        //backQueue.clear()
                    }
                }
            }, onFailure = {
                showMessage(R.string.not_have_user_or_invalid_password)
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
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
        viewModel.signInAndGetIsCompleted(email, password)
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
