package com.example.jf.features.post

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.marginTop
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentPostBinding
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.registration.domain.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private const val ARG_NAME_POST_ID = "id"
private const val ARG_NAME_USER_UID = "uid"

class PostFragment : Fragment(R.layout.fragment_post) {
    private lateinit var binding: FragmentPostBinding
    private lateinit var database: DatabaseReference
    private lateinit var layout: LinearLayout
    private val storageRef = Firebase.storage.reference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostBinding.bind(view)
        val currentUser = auth.currentUser
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        val id = arguments?.getString(ARG_NAME_POST_ID).toString()

        database.child("posts").child(id).get().addOnSuccessListener { it ->
            val post = it.getValue(Post::class.java)
            with(binding) {
                tvLoading.visibility = View.GONE
                layout = linearLayout
                if (post != null) {
                    post.userId?.let { it ->
                        database.child("users").child(it).get().addOnSuccessListener {
                            val user = it.getValue(User::class.java)
                            ivAvatar.load(user?.urlPhoto) {
                                transformations(CircleCropTransformation())
                            }
                            tvAuthor.text = user?.nick
                        }
                    }
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
                val uid = post?.userId
                author.setOnClickListener {
                    if (uid == currentUser?.uid)
                        view.findNavController().navigate(
                            R.id.action_postFragment_to_myProfileFragment
                        )
                    else {
                        view.findNavController().navigate(
                            R.id.action_postFragment_to_otherProfileFragment,
                            bundleOf(ARG_NAME_USER_UID to uid)
                        )
                    }
                }
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
