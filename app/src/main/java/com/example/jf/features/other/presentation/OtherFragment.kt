package com.example.jf.features.other.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentOtherBinding
import com.example.jf.utils.AppViewModelFactory
import javax.inject.Inject

class OtherFragment : Fragment(R.layout.fragment_other) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentOtherBinding
    private val viewModel: OtherViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherBinding.bind(view)
        initObservers()
        viewModel.onGetUser()
    }

    private fun initObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { user ->
                if (user == null) {
                    binding.btnNewPost.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.btnExit.visibility = View.GONE
                    binding.btnEditProfile.visibility = View.GONE
                    binding.btnSettings.visibility = View.GONE
                    binding.btnNotes.visibility = View.GONE
                }
                binding.btnLogin.setOnClickListener {
                    view?.findNavController()?.navigate(R.id.action_navigation_other_to_loginFragment)
                }
                binding.btnEditProfile.setOnClickListener {
                    view?.findNavController()
                        ?.navigate(R.id.action_navigation_other_to_editProfileFragment)
                }
                binding.btnNewPost.setOnClickListener {
                    view?.findNavController()
                        ?.navigate(R.id.action_navigation_other_to_newPostFragment)
                }
                binding.btnNotes.setOnClickListener {
                    view?.findNavController()
                        ?.navigate(R.id.action_navigation_other_to_notesFragment)
                }
                binding.btnSettings.setOnClickListener {
                    view?.findNavController()
                        ?.navigate(R.id.action_navigation_other_to_settingsFragment)
                }
                binding.btnExit.setOnClickListener {
                    viewModel.onSignOut()
                    view?.findNavController()?.navigate(R.id.navigation_main)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }
}
