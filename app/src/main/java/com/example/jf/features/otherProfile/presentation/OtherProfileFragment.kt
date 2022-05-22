package com.example.jf.features.otherProfile.presentation

import android.net.Uri
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
import com.example.jf.databinding.FragmentOtherProfileBinding
import com.example.jf.features.main.presentation.adapter.PostListAdapter
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_NAME_USER_ID = "uid"
private const val ARG_NAME_POST_ID = "id"

class OtherProfileFragment : Fragment(R.layout.fragment_other_profile) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentOtherProfileBinding
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30
    private val viewModel: OtherProfileViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherProfileBinding.bind(view)
        initObservers()

        val uid = arguments?.getString(ARG_NAME_USER_ID).toString()

        viewModel.onGetUserInfo(uid)
        viewModel.onGetPosts(postLimit, uid)

        with(binding) {

            posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && dy != 0) {
                        binding.tvLoadingPosts.visibility = View.VISIBLE
                        viewModel.onGetPosts(postLimit, uid)
                        observeMorePosts()
                    }
                }
            })
            swipeContainer.setOnRefreshListener {
                postLimit = 30
                viewModel.onGetPosts(postLimit, uid)
                swipeContainer.isRefreshing = false
            }
        }
    }

    private fun initObservers() {
        viewModel.userInfo.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                binding.ivAvatar.load(Uri.parse(it?.urlPhoto)) {
                    transformations(CircleCropTransformation())
                }
                binding.tvNick.text = it?.nick
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.posts.observe(viewLifecycleOwner) { it ->
            it?.fold(onSuccess = { posts ->
                binding.tvLoading.visibility = View.GONE

                postListAdapter =
                    PostListAdapter{
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

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
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
                R.id.action_otherProfileFragment_to_postFragment,
                bundleOf(ARG_NAME_POST_ID to it)
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
