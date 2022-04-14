package com.example.jf.features.cities.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.jf.features.cities.domain.entity.City
import com.example.jf.features.cities.presentation.diffutils.CityDiffItemCallback

class CityListAdapter(
    private val action: (String) -> Unit,
) : ListAdapter<City, CityHolder>(CityDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, action)

    override fun onBindViewHolder(holder: CityHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<City>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
