package com.example.jf.features.main.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            if (item.heading != null && item.heading.length > 20) {
                val heading = item.heading.subSequence(0, 19).toString() + "..."
                tvHeading.text = heading
            } else {
                tvHeading.text = item.heading
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
