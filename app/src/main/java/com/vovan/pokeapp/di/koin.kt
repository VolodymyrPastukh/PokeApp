package com.vovan.pokeapp.di

import com.vovan.pokeapp.data.NetworkPokemonRepository
import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.pokedetails.PokemonDetailsViewModel
import com.vovan.pokeapp.presentation.pokelist.PokemonListViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { providePokemonApiService() }
    single<PokemonRepository> { NetworkPokemonRepository(get()) }

    viewModel { PokemonDetailsViewModel(get()) }
    viewModel { PokemonListViewModel(get()) }
}

fun providePokemonApiService(): PokemonApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(PokemonApiService::class.java)
}