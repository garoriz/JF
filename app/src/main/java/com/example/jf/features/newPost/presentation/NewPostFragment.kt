package com.example.jf.features.newPost.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentNewPostBinding
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class NewPostFragment : Fragment(R.layout.fragment_new_post) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentNewPostBinding
    private var countMedia = 0
    private val urlsPhoto = mutableListOf<String>()
    private val urlsVideo = mutableListOf<String>()
    private val viewModel: NewPostViewModel by viewModels {
        factory
    }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { uploadFile(uri, "images") }
        }
    private val selectVideoFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { uploadFile(uri, "videos") }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewPostBinding.bind(view)
        initObservers()
        viewModel.onGetUser()
    }

    private fun initObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { user ->
                val popupMenu = PopupMenu(requireContext(), binding.tvAddMedia)
                popupMenu.inflate(R.menu.popup_menu_in_new_post_fragment)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.photo -> {
                            selectImageFromGallery()
                        }
                        R.id.video -> {
                            selectVideoFromGallery()
                        }
                    }
                    false
                }
                if (user != null) {
                    user.uid?.let { uid ->
                        binding.tvSend.setOnClickListener {
                            val post = Post(
                                uid,
                                binding.etHeading.text.toString(),
                                binding.etText.text.toString(),
                                urlsPhoto,
                                urlsVideo
                            )

                            if (post.text == "" &&
                                post.heading == "" &&
                                urlsPhoto.isEmpty() &&
                                urlsVideo.isEmpty()
                            ) {
                                showMessage(R.string.post_is_empty)
                                return@setOnClickListener
                            }

                            viewModel.addPost(post, uid)
                            showMessage(R.string.post_sent)
                            view?.findNavController()?.navigateUp()
                        }
                        binding.tvAddMedia.setOnClickListener {
                            popupMenu.show()
                        }
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun selectVideoFromGallery() = selectVideoFromGalleryResult.launch("video/*")

    private fun uploadFile(uri: Uri, type: String) {
        if (countMedia == 10) {
            showMessage(R.string.max_count_files)
            return
        }
        viewModel.uploadFileAndGetIsCompleted(uri, type)
        showMessage(R.string.loading_file)
        viewModel.isCompleted.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    val path = "${type}/${uri.lastPathSegment}"
                    when (type) {
                        "images" -> urlsPhoto.add(path)
                        "videos" -> urlsVideo.add(path)
                    }
                    showMessage(R.string.loading_success)
                    countMedia += 1
                    binding.tvCountMedia.text = countMedia.toString()
                } else {
                    showMessage(R.string.no_internet)
                }
                viewModel.isCompleted.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
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
