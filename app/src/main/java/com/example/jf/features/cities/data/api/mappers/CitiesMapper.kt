package com.example.jf.features.cities.data.api.mappers

import com.example.jf.features.cities.data.api.response.CitiesResponse
import com.example.jf.features.cities.data.api.response.Data
import com.example.jf.features.cities.domain.entity.City

class CitiesMapper {

    fun map(response: CitiesResponse): MutableList<City> {
        return response.data.map(this::map) as MutableList<City>
    }

    private fun map(data: Data): City = City(
        id = data.id,
        name = data.name
    )
}
