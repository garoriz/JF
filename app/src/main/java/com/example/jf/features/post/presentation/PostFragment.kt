package com.example.jf.features.post.presentation

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentPostBinding
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_NAME_POST_ID = "id"
private const val ARG_NAME_USER_UID = "uid"

class PostFragment : Fragment(R.layout.fragment_post) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentPostBinding
    private lateinit var layout: LinearLayout
    private val viewModel: PostViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostBinding.bind(view)
        initObservers()
        val id = arguments?.getString(ARG_NAME_POST_ID).toString()
        layout = binding.linearLayout
        viewModel.onGetPost(id)
    }

    private fun initObservers() {
        viewModel.post.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    tvLoading.visibility = View.GONE
                    if (it != null) {
                        it.userId?.let { userId ->
                            viewModel.onGetAuthor(userId)
                        }
                        tvHeading.text = it.heading
                        tvContent.text = it.text
                        if (it.urisPhoto != null)
                            for (uriPhoto in it.urisPhoto) {
                                viewModel.onGetPhotoUri(uriPhoto)
                            }
                        if (it.urisVideo != null)
                            for (uriVideo in it.urisVideo) {
                                viewModel.onGetVideoUri(uriVideo)
                            }
                    }
                    scrollView.fullScroll(View.FOCUS_UP)
                    val uid = it?.userId
                    initCurrentUserObserver(uid)
                    viewModel.onGetUser()
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.author.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                binding.ivAvatar.load(it?.urlPhoto) {
                    transformations(CircleCropTransformation())
                }
                binding.tvAuthor.text = it?.nick
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.photoUri.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                val imageView = ImageView(requireContext())
                imageView.load(it)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 8, 0, 0)
                params.gravity = Gravity.CENTER
                imageView.layoutParams = params
                layout.addView(imageView)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.videoUri.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                val videoView = VideoView(requireContext())
                val mc = MediaController(requireContext())
                videoView.setVideoURI(it)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1000
                )
                params.setMargins(0, 8, 0, 0)
                params.gravity = Gravity.CENTER
                videoView.layoutParams = params
                videoView.requestFocus()
                videoView.setOnPreparedListener {
                    mc.setAnchorView(videoView)
                }
                videoView.setMediaController(mc)
                binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
                    mc.hide()
                }
                layout.addView(videoView)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }

    private fun initCurrentUserObserver(userId: String?) {
        viewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { user ->
                binding.author.setOnClickListener {
                    if (userId == user?.uid)
                        view?.findNavController()?.navigate(
                            R.id.action_postFragment_to_myProfileFragment
                        )
                    else {
                        view?.findNavController()?.navigate(
                            R.id.action_postFragment_to_otherProfileFragment,
                            bundleOf(ARG_NAME_USER_UID to userId)
                        )
                    }
                }
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
