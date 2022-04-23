package com.example.jf.features.main.presentation.adapter

import android.content.ContentProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.jf.databinding.ItemPostBinding
import com.example.jf.features.main.domain.model.PostInList

class PostHolder(
    private val binding: ItemPostBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var postInList: PostInList? = null

    init {
        itemView.setOnClickListener {
            postInList?.id?.also(action)
        }
    }

    fun bind(item: PostInList) {
        this.postInList = item
        with(binding) {
            tvAuthor.text = item.author
            tvText.text = item.text
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
