package com.example.jf.features.myProfile.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentMyProfileBinding
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.model.User
import com.example.jf.features.myProfile.presentation.adapter.PostListAdapter
import com.example.jf.features.newPost.domain.model.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val ARG_NAME = "id"

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {
    private lateinit var binding: FragmentMyProfileBinding
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30
    private lateinit var viewModel: MyProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MyProfileViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyProfileBinding.bind(view)
        initUserObserver()
        viewModel.onGetUser()
    }

    private fun initUserObserver() {
        viewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { user ->
                if (user != null) {
                    user.uid?.let { uid ->
                        initObservers(uid)
                        viewModel.onGetPosts(postLimit, uid)
                    }
                    with(binding) {
                        ivAvatar.load(user.photoUrl) {
                            transformations(CircleCropTransformation())
                        }
                        tvNick.text = user.nick
                        btnEditProfile.setOnClickListener {
                            view?.findNavController()
                                ?.navigate(R.id.action_myProfileFragment_to_editProfileFragment)
                        }
                        posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                                    binding.tvLoadingPosts.visibility = View.VISIBLE
                                    user.uid?.let {
                                        viewModel.onGetPosts(postLimit, it)
                                    }
                                    observeMorePosts()
                                }
                            }
                        })
                        swipeContainer.setOnRefreshListener {
                            postLimit = 30
                            user.uid?.let { viewModel.onGetPosts(postLimit, it) }
                            swipeContainer.isRefreshing = false
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

    private fun initObservers(uid: String) {
        viewModel.posts.distinctUntilChanged().observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { posts ->
                binding.tvLoading.visibility = View.GONE

                postListAdapter = PostListAdapter({
                    getAllPost(it)
                    postListAdapter?.submitList(posts)
                }, {
                    deletePost(it, uid)
                    updatePosts(uid)
                })

                val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

                binding.posts.run {
                    adapter = postListAdapter
                    addItemDecoration(decorator)
                }

                postListAdapter?.submitList(posts)
                postLimit += 30
                viewModel.posts.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun observeMorePosts() {
        viewModel.posts.distinctUntilChanged().observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                postListAdapter?.submitList(it)
                binding.tvLoadingPosts.visibility = View.GONE
                postLimit += 30
                viewModel.posts.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun updatePosts(uid: String) {
        postLimit = 30
        viewModel.onGetPosts(postLimit, uid)
    }

    private fun getAllPost(it: String) {
        view?.findNavController()
            ?.navigate(
                R.id.action_myProfileFragment_to_postFragment,
                bundleOf(ARG_NAME to it)
            )
    }

    private fun deletePost(it: String, uid: String) {
        viewModel.deletePost(it, uid)
    }
}
