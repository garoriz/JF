package com.example.jf.features.main.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.presentation.diffutils.PostDiffItemCallback

class PostListAdapter(
    private val action: (String) -> Unit,
) : ListAdapter<PostInList, PostHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder = PostHolder.create(parent, action)

    override fun onBindViewHolder(holder: PostHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<PostInList?>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
