package com.garv.pokemon

data class CategoriesResponse(val results: List<pokemonData>)

data class pokemonData(
    val name: String,
    val url :String
)
