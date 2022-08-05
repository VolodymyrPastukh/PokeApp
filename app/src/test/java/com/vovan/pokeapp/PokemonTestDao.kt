package com.vovan.pokeapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vovan.pokeapp.data.room.AppDatabase
import com.vovan.pokeapp.data.room.PokemonsDao
import com.vovan.pokeapp.data.room.entity.PokemonDB
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PokemonTestDao {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var appDatabase: AppDatabase
    private lateinit var pokeDao: PokemonsDao

    @Before
    fun setup() {
        appDatabase = AppDatabase.getDatabaseInMemory(context)
        pokeDao = appDatabase.pokemonDao()
    }

    @After
    fun clean() {
        appDatabase.close()
        stopKoin()
    }

    @Test
    fun insertPokemon() = runTest {
        val pokemonDB = PokemonDB(
            111,
            "image",
            "testPokemon",
            1,
            10,
            10
        )
        pokeDao.insertAll(pokemonDB)
        val pokemons = pokeDao.getAllPokemons()
        pokemons shouldNotBe null
        pokemons shouldNotBe emptyList<PokemonDB>()
        pokemons shouldContain pokemonDB
    }

    @Test
    fun insertPokemonWithConflict() = runTest {
        val pokemon1 = PokemonDB(
            111,
            "image",
            "testPokemon1",
            1,
            10,
            10
        )
        val pokemon2 = PokemonDB(
            111,
            "image",
            "testPokemon2",
            1,
            10,
            10
        )

        pokeDao.insertAll(pokemon1, pokemon2)
        val storedPokemon = pokeDao.getPokemonById(111)
        storedPokemon shouldNotBe null
        storedPokemon shouldBe pokemon1
        storedPokemon shouldNotBe pokemon2
    }

    @Test
    fun getPokemonById() = runTest {
        val pokemon1 = PokemonDB(
            111,
            "image",
            "testPokemon1",
            1,
            10,
            10
        )
        val pokemon2 = PokemonDB(
            112,
            "image",
            "testPokemon2",
            1,
            10,
            10
        )

        pokeDao.insertAll(pokemon1, pokemon2)
        val storedPokemon1 = pokeDao.getPokemonById(111)
        val storedPokemon2 = pokeDao.getPokemonById(112)
        storedPokemon1 shouldNotBe null
        storedPokemon1 shouldNotBe null
        storedPokemon1 shouldBe pokemon1
        storedPokemon2 shouldBe pokemon2
    }

    @Test
    fun deletePokemon() = runTest {
        val pokemon = PokemonDB(
            111,
            "image",
            "testPokemon",
            1,
            10,
            10
        )
        val pokemons = pokeDao.getAllPokemons()
        pokeDao.delete(pokemon)
        pokemons shouldBe emptyList()
        pokemons shouldNotContain pokemon
    }
}
