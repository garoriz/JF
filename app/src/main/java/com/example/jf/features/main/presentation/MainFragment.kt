package com.example.jf.features.main.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentMainBinding
import com.example.jf.features.main.presentation.adapter.PostListAdapter
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_NAME = "id"

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentMainBinding
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30
    private val viewModel: MainViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
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
                        if (it.photoUrl != null)
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
            it?.fold(onSuccess = { posts ->
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
                viewModel.clearPostsLiveData()
                postLimit += 30
                viewModel.posts.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun observeMorePosts() {
        viewModel.posts.observe(viewLifecycleOwner) { it ->
            it?.fold(onSuccess = {
                postListAdapter?.submitList(it)
                viewModel.clearPostsLiveData()
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

}
