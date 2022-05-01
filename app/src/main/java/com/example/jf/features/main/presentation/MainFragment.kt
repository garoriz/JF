package com.example.jf.features.main.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentMainBinding
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.adapter.PostListAdapter
import com.example.jf.features.newPost.domain.model.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private const val ARG_NAME = "id"

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val storageRef = Firebase.storage.reference
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        val currentUser = auth.currentUser

        database = Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference

        updatePosts()

        with(binding) {
            if (currentUser != null) {
                ivAvatar.load(currentUser.photoUrl) {
                    transformations(CircleCropTransformation())
                }
                ivAvatar.setOnClickListener {
                    view.findNavController().navigate(R.id.action_navigation_main_to_myProfileFragment)
                }
            }
            else
                ivAvatar.setOnClickListener {
                    view.findNavController().navigate(R.id.action_navigation_main_to_loginFragment)
                }
            swipeContainer.setOnRefreshListener {
                updatePosts()
                swipeContainer.isRefreshing = false
            }

            posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && dy != 0) {
                        binding.tvLoadingPosts.visibility = View.VISIBLE
                        val posts = database.child("posts").limitToLast(postLimit)

                        posts.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val postList = mutableListOf<PostInList?>()
                                for (postSnapshot in dataSnapshot.children) {
                                    val post = postSnapshot.getValue(Post::class.java)
                                    postList.add(PostInList(
                                        postSnapshot.key,
                                        post?.userId,
                                        post?.text,
                                        post?.urisPhoto?.get(0),
                                        post?.urisVideo?.get(0),
                                    ))
                                }
                                postList.reverse()

                                binding.tvLoadingPosts.visibility = View.GONE
                                postListAdapter?.submitList(postList)
                                posts.removeEventListener(this)
                                postLimit += 30
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                showMessage(R.string.no_internet)
                            }
                        })
                    }
                }
            })
        }
    }

    private fun updatePosts() {
    val posts = database.child("posts").limitToLast(postLimit)

        posts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.tvLoading.visibility = View.GONE
                val postList = mutableListOf<PostInList?>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    postList.add(PostInList(
                        postSnapshot.key,
                        post?.userId,
                        post?.text,
                        post?.urisPhoto?.get(0),
                        post?.urisVideo?.get(0),
                    ))
                }
                postList.reverse()
                postListAdapter = PostListAdapter {
                    getAllPost(it)
                    postListAdapter?.submitList(postList)
                }

                val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

                binding.posts.run {
                    adapter = postListAdapter
                    addItemDecoration(decorator)
                }

                postListAdapter?.submitList(postList)
                posts.removeEventListener(this)
                postLimit += 30
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showMessage(R.string.no_internet)
            }
        })
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
