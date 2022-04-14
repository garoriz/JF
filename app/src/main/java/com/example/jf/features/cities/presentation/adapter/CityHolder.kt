package com.example.jf.features.cities.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jf.databinding.ItemCityBinding
import com.example.jf.features.cities.domain.entity.City

class CityHolder(
    private val binding: ItemCityBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var city: City? = null

    init {
        itemView.setOnClickListener {
            city?.name?.also(action)
        }
    }

    fun bind(item: City) {
        this.city = item
        with(binding) {
            tvCityName.text = item.name
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (String) -> Unit
        ) = CityHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
