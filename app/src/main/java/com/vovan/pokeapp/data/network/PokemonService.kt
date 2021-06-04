package com.vovan.pokeapp.data.network

import com.vovan.pokeapp.data.dto.GenerationDetailedDTO
import com.vovan.pokeapp.data.dto.PokemonDetailedDTO
import com.vovan.pokeapp.data.dto.PokemonListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonApiService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 2,
        @Query("offset") offset: Int = 0
    ): PokemonListDTO

    @GET("generation/{level}")
    suspend fun fetchPokemonGeneration(@Path("level") level: Int = 0): GenerationDetailedDTO

    @GET("pokemon/{id}")
    suspend fun fetchPokemonInfo(@Path("id") id: Int): PokemonDetailedDTO
}

