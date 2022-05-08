package com.example.jf.features.otherProfile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.FragmentOtherProfileBinding
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.adapter.PostListAdapter
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.registration.domain.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private const val ARG_NAME_USER_ID = "uid"
private const val ARG_NAME_POST_ID = "id"

class OtherProfileFragment : Fragment(R.layout.fragment_other_profile) {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val storageRef = Firebase.storage.reference
    private lateinit var binding: FragmentOtherProfileBinding
    private var postListAdapter: PostListAdapter? = null
    var postLimit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherProfileBinding.bind(view)
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

        val uid = arguments?.getString(ARG_NAME_USER_ID).toString()

        updatePosts(uid)

        with(binding) {
            database.child("users").child(uid).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                ivAvatar.load(Uri.parse(user?.urlPhoto)) {
                    transformations(CircleCropTransformation())
                }
                tvNick.text = user?.nick
            }.addOnFailureListener {
                showMessage(R.string.no_internet)
            }

            btnMessages.setOnClickListener {
                if (auth.currentUser == null) {
                    showMessage(R.string.login_and_start_chat)
                    view.findNavController().navigate(R.id.action_otherProfileFragment_to_loginFragment)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_otherProfileFragment_to_chatFragment,
                            bundleOf(ARG_NAME_USER_ID to uid))
                }
            }

            posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && dy != 0) {
                        binding.tvLoadingPosts.visibility = View.VISIBLE
                        val posts = database
                            .child("users")
                            .child(uid)
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
                updatePosts(uid)
                swipeContainer.isRefreshing = false
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
                postListAdapter =
                    PostListAdapter {
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
