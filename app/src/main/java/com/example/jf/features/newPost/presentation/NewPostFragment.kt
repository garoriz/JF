package com.example.jf.features.newPost.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentNewPostBinding
import com.example.jf.features.newPost.presentation.domain.model.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class NewPostFragment : Fragment(R.layout.fragment_new_post) {

    private lateinit var binding: FragmentNewPostBinding
    private lateinit var database: DatabaseReference
    private val storageRef = Firebase.storage.reference
    private var countMedia = 0
    private val urlsPhoto = mutableListOf<String>()
    private val urlsVideo = mutableListOf<String>()
    private val urlsMusic = mutableListOf<String>()

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uploadFile(uri, "images") }
    }
    private val selectVideoFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uploadFile(uri, "videos") }
    }
    private val selectMusicFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uploadFile(uri, "musics") }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewPostBinding.bind(view)

        database = Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference

        val popupMenu = PopupMenu(requireContext(), binding.tvAddMedia)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.photo -> {
                    selectImageFromGallery()
                }
                R.id.video -> {
                    selectVideoFromGallery()
                }
                R.id.music -> {
                    selectMusicFromGallery()
                }
            }
            false
        }

        with (binding) {
            tvSend.setOnClickListener {
                val post = Post(etText.text.toString(), urlsPhoto, urlsVideo, urlsMusic)

                if (post.text == "" &&
                    urlsPhoto.isEmpty()  &&
                    urlsVideo.isEmpty() &&
                    urlsMusic.isEmpty()
                ) {
                    showMessage(R.string.post_is_empty)
                    return@setOnClickListener
                }

                database.child("posts")
                    .push()
                    .setValue(post)
                view.findNavController().navigateUp()
                showMessage(R.string.post_sent)
            }
            tvAddMedia.setOnClickListener {
                popupMenu.show()
            }
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun selectVideoFromGallery() = selectVideoFromGalleryResult.launch("video/*")

    private fun selectMusicFromGallery() = selectMusicFromGalleryResult.launch("audio/*")

    private fun uploadFile(uri: Uri, type: String) {
        if (countMedia == 10) {
            showMessage(R.string.max_count_files)
            return
        }
        val path = "${type}/${uri.lastPathSegment}"
        val uploadTask = storageRef.child(path).putFile(uri)
        showMessage(R.string.loading)

        uploadTask.addOnFailureListener {
            showMessage(R.string.no_internet)
        }.addOnSuccessListener {
            when (type) {
                "images" -> urlsPhoto.add(path)
                "videos" -> urlsVideo.add(path)
                "musics" -> urlsMusic.add(path)
            }
            showMessage(R.string.loading_success)
            countMedia += 1
            binding.tvCountMedia.text = countMedia.toString()
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
