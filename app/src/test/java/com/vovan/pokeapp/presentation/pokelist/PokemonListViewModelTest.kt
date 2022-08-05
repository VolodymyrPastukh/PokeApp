package com.vovan.pokeapp.presentation.pokelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import java.lang.IllegalStateException

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class PokemonListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val pokemon = PokemonEntity(id = 1, name = "name", height = 0, weight = 0, order = 0)

    private val repository = mockk<PokemonRepository>()
    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(dispatcher)
        coEvery { repository.allPokemons } returns flow {
            repeat(2) {
                delay(1000)
                emit(Result.Success(listOf(pokemon, pokemon)))
            }
            delay(3000)
            emit(Result.Error(IllegalStateException("No items")))
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
        stopKoin()
    }

    @Test
    fun `test default state flow`() = runTest {
        val job = launch {
            viewModel = PokemonListViewModel(repository)
            val result = viewModel.state.value
            result?.javaClass shouldBe PokemonListViewState.Loading::class.java
        }

        job.join()
        job.cancel()
    }

    @Test
    fun `test pokemons in respond flow`() = runTest {
        val job = launch {
            viewModel = PokemonListViewModel(repository)
            advanceTimeBy(1500)
            val result = viewModel.state.value
            result?.javaClass shouldBe PokemonListViewState.Data::class.java
            if (result is PokemonListViewState.Data) result.pokemons shouldHaveAtLeastSize 1
        }

        job.join()
        job.cancel()

    }


    @Test
    fun `test error in respond flow`() = runTest {
        val job = launch {
            viewModel = PokemonListViewModel(repository)
            advanceTimeBy(8000)
            val result = viewModel.state.value
            result?.javaClass shouldBe PokemonListViewState.Error::class.java
        }

        job.join()
        job.cancel()
    }


}