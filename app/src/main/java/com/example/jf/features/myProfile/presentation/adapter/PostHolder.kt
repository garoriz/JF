package com.example.jf.features.myProfile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.databinding.ItemPostInMyFragmentBinding
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.registration.domain.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostHolder(
    private val binding: ItemPostInMyFragmentBinding,
    private val getAllPost: (String) -> Unit,
    private val delete: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private val database: DatabaseReference =
        Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
    private var postInList: PostInList? = null

    init {
        itemView.setOnClickListener {
            postInList?.id?.also(getAllPost)
        }
        binding.tvDeletePost.setOnClickListener {
            postInList?.id?.also(delete)
        }
    }

    fun bind(item: PostInList) {
        this.postInList = item
        with(binding) {
            item.userId?.let { it ->
                database.child("users").child(it).get().addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    ivAvatar.load(user?.urlPhoto) {
                        transformations(CircleCropTransformation())
                    }
                    tvAuthor.text = user?.nick
                }
            }
            if (item.text != null && item.text.length > 250) {
                val text = item.text.subSequence(0, 249).toString() + "..."
                tvText.text = text
            } else {
                tvText.text = item.text
            }
            if (item.uriPhoto != null) {
                tvPhoto.visibility = View.VISIBLE
            }
            if (item.uriVideo != null) {
                tvVideo.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (String) -> Unit,
            delete: (String) -> Unit
        ) = PostHolder(
            ItemPostInMyFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action, delete
        )
    }
}
