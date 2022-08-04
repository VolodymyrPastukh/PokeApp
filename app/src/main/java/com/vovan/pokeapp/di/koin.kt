package com.vovan.pokeapp.di

import android.content.Context
import androidx.room.Room
import com.vovan.pokeapp.data.NetworkPokemonRepository
import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.data.room.AppDatabase
import com.vovan.pokeapp.data.room.PokemonsDao
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.pokedetails.PokemonDetailsViewModel
import com.vovan.pokeapp.presentation.pokelist.PokemonListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {


    single { provideExternalCoroutineScope() }

    single { provideOkHttpClient() }
    single { providePokemonApiService(get()) }
    single { provideDatabase(androidContext()) }
    single<PokemonRepository> { NetworkPokemonRepository(get(), providePokemonDao(get()) , get()) }


    viewModel { (id: Int) -> PokemonDetailsViewModel(id, get()) }
    viewModel { PokemonListViewModel(get()) }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .readTimeout(15, TimeUnit.SECONDS)
    .connectTimeout(15, TimeUnit.SECONDS)
    .build()

fun providePokemonApiService(okHttpClient: OkHttpClient): PokemonApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(PokemonApiService::class.java)
}

fun provideDatabase(appContext: Context): AppDatabase =
    Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "pokemonDatabase"
    ).build()

fun providePokemonDao(appDatabase: AppDatabase): PokemonsDao = appDatabase.pokemonDao()

fun provideExternalCoroutineScope(): CoroutineScope =
    CoroutineScope(Dispatchers.IO + SupervisorJob())