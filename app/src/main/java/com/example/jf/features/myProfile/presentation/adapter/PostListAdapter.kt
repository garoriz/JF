package com.example.jf.features.myProfile.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.presentation.diffutils.PostDiffItemCallback

class PostListAdapter(
    private val action: (String) -> Unit,
    private val delete: (String) -> Unit,
) : ListAdapter<PostInList, PostHolder>(PostDiffItemCallback())  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder = PostHolder.create(parent, action, delete)

    override fun onBindViewHolder(holder: PostHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<PostInList?>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
