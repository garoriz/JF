package com.example.jf.features.main.presentation

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
import com.example.jf.databinding.FragmentMainBinding
import com.example.jf.features.main.presentation.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar

private const val ARG_NAME = "id"

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        initObservers()

        viewModel.onGetUser()
        viewModel.onGetPosts(postLimit)

        with(binding) {
            swipeContainer.setOnRefreshListener {
                postLimit = 30
                observePosts()
                swipeContainer.isRefreshing = false
            }

            posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && dy != 0) {
                        binding.tvLoadingPosts.visibility = View.VISIBLE
                        viewModel.onGetPosts(postLimit)
                        observeMorePosts()
                    }
                }
            })
        }
    }

    private fun initObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    if (it != null) {
                        ivAvatar.load(it.photoUrl) {
                            transformations(CircleCropTransformation())
                        }
                        ivAvatar.setOnClickListener {
                            view?.findNavController()
                                ?.navigate(R.id.action_navigation_main_to_myProfileFragment)
                        }
                    } else
                        ivAvatar.setOnClickListener {
                            view?.findNavController()
                                ?.navigate(R.id.action_navigation_main_to_loginFragment)
                        }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        observePosts()

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }

    private fun observePosts() {
        viewModel.posts.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { posts ->
                binding.tvLoading.visibility = View.GONE
                postListAdapter = PostListAdapter {
                    getAllPost(it)
                    postListAdapter?.submitList(posts)
                }

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

    private fun getAllPost(it: String) {
        view?.findNavController()
            ?.navigate(
                R.id.action_navigation_main_to_postFragment,
                bundleOf(ARG_NAME to it)
            )
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
