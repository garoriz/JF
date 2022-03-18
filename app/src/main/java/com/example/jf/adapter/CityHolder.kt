package com.example.jf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jf.data.api.response.cities.Data
import com.example.jf.databinding.ItemCityBinding

class CityHolder(
    private val binding: ItemCityBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var data: Data? = null

    init {
        itemView.setOnClickListener {
            data?.wikiDataId?.also(action)
        }
    }

    fun bind(item: Data) {
        this.data = item
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
