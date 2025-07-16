package com.garv.pokemon

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val _retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create()).build()

val pokemonService = _retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("pokemon?limit=200&offset=0")
    suspend fun getCategories(): CategoriesResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetail

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(@Path("name") name: String): PokemonSpecies


}