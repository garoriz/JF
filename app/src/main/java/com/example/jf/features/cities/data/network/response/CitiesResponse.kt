package com.example.jf.features.cities.data.network.response

data class CitiesResponse(
    val data: List<Data>,
    val links: List<Link>,
    val metadata: Metadata
)
