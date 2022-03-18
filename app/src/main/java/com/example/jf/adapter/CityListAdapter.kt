package com.example.jf.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.jf.data.api.response.cities.Data
import com.example.jf.diffutils.CityDiffItemCallback

class CityListAdapter(
    private val action: (String) -> Unit,
) : ListAdapter<Data, CityHolder>(CityDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, action)

    override fun onBindViewHolder(holder: CityHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Data>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
