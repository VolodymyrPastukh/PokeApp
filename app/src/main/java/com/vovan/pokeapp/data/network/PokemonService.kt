package com.vovan.pokeapp.data.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

fun createPokemonApiService(): PokemonApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(PokemonApiService::class.java)
}

interface PokemonApiService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListDTO

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(@Path("name") name: String): PokemonDetailedDTO
}

data class PokemonListDTO(
    val count: Int,
    val results: List<PokemonPartialDTO>,
)

data class PokemonPartialDTO(
    val name: String,
    val url: String
)

data class PokemonDetailedDTO(
    val id: Int,
    val name: String,
    val abilities: List<PokemonAbilityData>
)

data class PokemonAbilityDetailsData(
    val name: String,
    val url: String
)

data class PokemonAbilityData(
    val ability: PokemonAbilityDetailsData,
    val is_hidden: Boolean,
    val slot: Int,
)
