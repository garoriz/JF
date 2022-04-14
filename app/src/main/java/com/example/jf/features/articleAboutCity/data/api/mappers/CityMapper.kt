package com.example.jf.features.articleAboutCity.data.api.mappers

import com.example.jf.features.articleAboutCity.data.api.response.CityResponse
import com.example.jf.features.articleAboutCity.domain.entity.City

private const val DEFAULT_SOURCE_PHOTO =
    "https://media.istockphoto.com/vectors/illustration-of-shadow-of-a-city-town-vector-id1201342349?k=20&m=1201342349&s=170667a&w=0&h=m92F6r4tx8QhV-gduM4viHgZYjHtZ-yBqC4DsjUWXyU="

class CityMapper {
    fun map(cityResponse: CityResponse): City = City(
        title = cityResponse.query.pages[0].title,
        description = cityResponse.query.pages[0].extract,
        sourcePhoto = cityResponse.query.pages[0].original?.source
            ?: DEFAULT_SOURCE_PHOTO
    )
}
