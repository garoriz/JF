package com.example.jf.features.main.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.jf.R
import com.example.jf.databinding.FragmentMainBinding
import com.example.jf.features.main.domain.model.Post
import com.example.jf.features.main.presentation.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


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
                ivAvatar.load(currentUser.photoUrl)
                ivAvatar.setOnClickListener {
                    auth.signOut()
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
                                val postList = mutableListOf<Post?>()
                                for (postSnapshot in dataSnapshot.children) {
                                    postList.add(postSnapshot.getValue(Post::class.java))
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
                val postList = mutableListOf<Post?>()
                for (postSnapshot in dataSnapshot.children) {
                    postList.add(postSnapshot.getValue(Post::class.java))
                }
                postList.reverse()
                postListAdapter = PostListAdapter {
                    getAllPost(it)
                    postListAdapter?.submitList(postList)
                }

                binding.posts.run {
                    adapter = postListAdapter
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

    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
