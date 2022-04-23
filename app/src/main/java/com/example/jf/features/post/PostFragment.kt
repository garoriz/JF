package com.example.jf.features.post

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginTop
import coil.load
import com.example.jf.R
import com.example.jf.databinding.FragmentPostBinding
import com.example.jf.features.newPost.domain.model.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private const val ARG_NAME = "id"

class PostFragment : Fragment(R.layout.fragment_post) {
    private lateinit var binding: FragmentPostBinding
    private lateinit var database: DatabaseReference
    private lateinit var layout: LinearLayout
    private val storageRef = Firebase.storage.reference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostBinding.bind(view)
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        val id = arguments?.getString(ARG_NAME).toString()

        database.child("posts").child(id).get().addOnSuccessListener { it ->
            val post = it.getValue(Post::class.java)
            with(binding) {
                tvLoading.visibility = View.GONE
                layout = linearLayout
                if (post != null) {
                    tvAuthor.text = post.author
                    tvContent.text = post.text
                    if (post.urisPhoto != null)
                        for (uriPhoto in post.urisPhoto) {
                            val imageView = ImageView(requireContext())
                            storageRef.child(uriPhoto).downloadUrl.addOnSuccessListener {
                                imageView.load(it)
                                val params = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                params.setMargins(0, 8, 0, 0)
                                imageView.layoutParams = params
                            }.addOnFailureListener {
                                showMessage(R.string.error_loading_media)
                            }
                            layout.addView(imageView)
                        }
                    if (post.urisVideo != null)
                        for (uriVideo in post.urisVideo) {
                            val videoView = VideoView(requireContext())
                            val mc = MediaController(requireContext())
                            storageRef.child(uriVideo).downloadUrl.addOnSuccessListener {
                                videoView.setVideoURI(it)
                                val params = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    1000
                                )
                                params.setMargins(0, 8, 0, 0)
                                params.gravity = Gravity.CENTER
                                videoView.layoutParams = params
                                videoView.requestFocus()
                            }.addOnFailureListener {
                                showMessage(R.string.error_loading_media)
                            }
                            videoView.setOnPreparedListener {
                                mc.setAnchorView(videoView)
                            }
                            videoView.setMediaController(mc)
                            scrollView.viewTreeObserver.addOnScrollChangedListener {
                                mc.hide()
                            }
                            layout.addView(videoView)
                        }
                }
                scrollView.fullScroll(View.FOCUS_UP)
            }
        }.addOnFailureListener {
            binding.tvLoading.visibility = View.GONE
            showMessage(R.string.no_internet)
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
