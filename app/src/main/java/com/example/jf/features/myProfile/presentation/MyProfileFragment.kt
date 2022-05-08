package com.example.jf.features.myProfile.presentation

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
import com.example.jf.databinding.FragmentMyProfileBinding
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.presentation.adapter.PostListAdapter
import com.example.jf.features.newPost.domain.model.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val ARG_NAME = "id"

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {
    private lateinit var binding: FragmentMyProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyProfileBinding.bind(view)
        val currentUser = auth.currentUser
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        if (currentUser != null) {
            updatePosts(currentUser.uid)
        }

        if (currentUser != null) {
            with(binding) {
                ivAvatar.load(currentUser.photoUrl) {
                    transformations(CircleCropTransformation())
                }
                tvNick.text = currentUser.displayName
                btnEditProfile.setOnClickListener {
                    view.findNavController()
                        .navigate(R.id.action_myProfileFragment_to_editProfileFragment)
                }

                posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (!recyclerView.canScrollVertically(1) && dy != 0) {
                            binding.tvLoadingPosts.visibility = View.VISIBLE
                            val posts = database
                                .child("users")
                                .child(currentUser.uid)
                                .child("posts")
                                .limitToLast(postLimit)

                            posts.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val postList = mutableListOf<PostInList?>()
                                    for (postSnapshot in dataSnapshot.children) {
                                        val post = postSnapshot.getValue(Post::class.java)
                                        postList.add(
                                            PostInList(
                                                postSnapshot.key,
                                                post?.userId,
                                                post?.text,
                                                post?.urisPhoto?.get(0),
                                                post?.urisVideo?.get(0),
                                            )
                                        )
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
                swipeContainer.setOnRefreshListener {
                    updatePosts(currentUser.uid)
                    swipeContainer.isRefreshing = false
                }
            }
        }
    }

    private fun updatePosts(uid: String) {
        val posts = database
            .child("users")
            .child(uid)
            .child("posts")
            .limitToLast(postLimit)

        posts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.tvLoading.visibility = View.GONE
                val postList = mutableListOf<PostInList?>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    postList.add(
                        PostInList(
                            postSnapshot.key,
                            post?.userId,
                            post?.text,
                            post?.urisPhoto?.get(0),
                            post?.urisVideo?.get(0),
                        )
                    )
                }
                postList.reverse()
                postListAdapter = PostListAdapter({
                    getAllPost(it)
                    postListAdapter?.submitList(postList)
                }, {
                    deletePost(it, uid)
                    updatePosts(uid)
                })

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
                R.id.action_myProfileFragment_to_postFragment,
                bundleOf(ARG_NAME to it)
            )
    }

    private fun deletePost(it: String, uid: String) {
        database
            .child("posts")
            .child(it)
            .removeValue()
        database
            .child("users")
            .child(uid)
            .child("posts")
            .child(it)
            .removeValue()
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
