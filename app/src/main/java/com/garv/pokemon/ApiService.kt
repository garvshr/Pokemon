package com.garv.pokemon

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val _retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create()).build()

val pokemonService = _retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("pokemon?limit=200&offset=0")
    suspend fun getCategories(): CategoriesResponse
}