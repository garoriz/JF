package com.example.jf.features.settings.presentation

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
import com.example.jf.databinding.FragmentSettingsBinding
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        initObservers()

        with(binding) {
            btnChangePassword.setOnClickListener {
                if (isValidCredentials(
                        etPassword.text.toString(),
                        etConfirmedPassword.text.toString()
                    )
                ) {
                    viewModel.onChangePassword(etPassword.text.toString())
                    showMessage(R.string.change_password_successfully)
                    view.findNavController().navigateUp()
                }
                ViewCompat.getWindowInsetsController(requireView())
                    ?.hide(WindowInsetsCompat.Type.ime())
            }
        }
    }

    private fun initObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
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
