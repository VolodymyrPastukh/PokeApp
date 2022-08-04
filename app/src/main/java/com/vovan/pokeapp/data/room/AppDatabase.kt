package com.vovan.pokeapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vovan.pokeapp.data.room.entity.PokemonDB

@Database(entities = [PokemonDB::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun pokemonDao(): PokemonsDao
}