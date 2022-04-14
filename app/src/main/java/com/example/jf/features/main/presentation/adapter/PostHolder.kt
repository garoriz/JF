package com.example.jf.features.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jf.databinding.ItemPostBinding
import com.example.jf.features.main.domain.model.Post

class PostHolder(
    private val binding: ItemPostBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var post: Post? = null

    init {
        itemView.setOnClickListener {
            post?.text?.also(action)
        }
    }

    fun bind(item: Post) {
        this.post = item
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
