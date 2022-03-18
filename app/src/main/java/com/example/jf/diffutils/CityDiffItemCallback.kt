package com.example.jf.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.jf.data.api.response.cities.Data

class CityDiffItemCallback  : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(
        oldItem: Data,
        newItem: Data
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Data,
        newItem: Data
    ): Boolean = oldItem == newItem
}
