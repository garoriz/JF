package com.example.jf.features.cities.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.jf.features.cities.domain.entity.City

class CityDiffItemCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(
        oldItem: City,
        newItem: City
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: City,
        newItem: City
    ): Boolean = oldItem == newItem
}
