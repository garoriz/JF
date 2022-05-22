package com.example.jf.features.editProfile.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentEditProfileBinding
import com.example.jf.features.editProfile.domain.models.User
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels {
        factory
    }
    private var currentUser: User? = null
    private lateinit var selectedAvatarUri: Uri

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                showMessage(R.string.loading_file)
                selectedAvatarUri = it
                editProfileViewModel.uploadAvatarAndGetIsCompleted(it)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditProfileBinding.bind(view)
        initObservers()

        editProfileViewModel.onGetUser()
    }

    private fun initObservers() {
        editProfileViewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                currentUser = it
                with(binding) {
                    ivAvatar.load(it?.photoUrl) {
                        transformations(CircleCropTransformation())
                    }
                    etNick.setText(it?.nick)
                    btnChangePhoto.setOnClickListener {
                        selectImageFromGallery()
                    }
                    btnSave.setOnClickListener {
                        editProfileViewModel.updateNick(etNick.text.toString())
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        editProfileViewModel.isCompletedUpdatingNick.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    currentUser?.uid?.let { uid ->
                        editProfileViewModel.updateNickInDb(
                            binding.etNick.text.toString(),
                            uid
                        )
                    }
                    ViewCompat.getWindowInsetsController(requireView())
                        ?.hide(WindowInsetsCompat.Type.ime())
                    showMessage(R.string.nick_saved_successfully)
                } else {
                    showMessage(R.string.no_internet)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        editProfileViewModel.isCompletedUploadingAvatar.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    editProfileViewModel.onGetDownloadAvatarUri(selectedAvatarUri)
                } else {
                    showMessage(R.string.no_internet)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        editProfileViewModel.downloadAvatarUri.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    editProfileViewModel.updateAvatarUriAndGetIsCompleted(it)
                } else {
                    showMessage(R.string.error_loading_media)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        editProfileViewModel.isCompletedUpdatingAvatarUri.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    editProfileViewModel.onGetUser()
                    currentUser?.uid?.let { uid ->
                        currentUser?.photoUrl?.let { avatarUri ->
                            editProfileViewModel.updateAvatarUriInDb(
                                uid,
                                avatarUri
                            )
                            binding.ivAvatar.load(avatarUri) {
                                transformations(CircleCropTransformation())
                            }
                        }
                    }
                } else {
                    showMessage(R.string.no_internet)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        editProfileViewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
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
