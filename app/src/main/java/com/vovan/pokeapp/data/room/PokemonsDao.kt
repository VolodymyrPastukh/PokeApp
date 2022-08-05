package com.vovan.pokeapp.data.room

import androidx.room.*
import com.vovan.pokeapp.data.room.entity.PokemonDB

@Dao
interface PokemonsDao {

    @Query("SELECT * FROM pokemondb")
    suspend fun getAllPokemons(): List<PokemonDB>

    @Query("SELECT * FROM pokemondb WHERE pokemondb.id LIKE :id")
    suspend fun getPokemonById(id: Int): PokemonDB?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg pokemons: PokemonDB)

    @Delete
    suspend fun delete(pokemon: PokemonDB)

}