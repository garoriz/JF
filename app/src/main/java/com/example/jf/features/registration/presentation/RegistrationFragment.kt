package com.example.jf.features.registration.presentation

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
import com.example.jf.databinding.FragmentRegistrationBinding
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels {
        factory
    }
    private lateinit var nick: String

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegistrationBinding.bind(view)
        initObservers()

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

    private fun initObservers() {
        viewModel.uid.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    viewModel.onAddUserInDb(nick, it)
                    view?.findNavController()?.navigate(
                        R.id.action_registrationFragment_to_navigation_main
                    )
                }
                viewModel.uid.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
                showMessage(R.string.error_registration)
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
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
        viewModel.onCreateUser(email, password, nick)
        this.nick = nick
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
