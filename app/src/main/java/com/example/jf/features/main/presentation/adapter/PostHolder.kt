package com.example.jf.features.main.presentation.adapter

import android.content.ContentProvider
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jf.R
import com.example.jf.databinding.ItemPostBinding
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.MainFragment
import com.example.jf.features.registration.domain.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PostHolder(
    private val binding: ItemPostBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val database: DatabaseReference = Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
        .reference
    private var postInList: PostInList? = null

    init {
        itemView.setOnClickListener {
            postInList?.id?.also(action)
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
            action: (String) -> Unit
        ) = PostHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
