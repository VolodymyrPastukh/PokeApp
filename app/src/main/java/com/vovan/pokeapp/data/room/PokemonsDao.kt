package com.vovan.pokeapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vovan.pokeapp.data.room.entity.PokemonDB

@Dao
interface PokemonsDao {

    @Query("SELECT * FROM pokemondb")
    fun getAll(): List<PokemonDB>

    @Query("SELECT * FROM pokemondb WHERE pokemondb.id LIKE :id")
    fun getPokemonById(id: Int): PokemonDB?

    @Insert
    fun insertAll(vararg pokemons: PokemonDB)

    @Delete
    fun delete(pokemon: PokemonDB)

}