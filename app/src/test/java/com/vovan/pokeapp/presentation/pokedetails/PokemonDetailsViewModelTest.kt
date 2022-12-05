package com.vovan.pokeapp.presentation.pokedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.presentation.MainCoroutineRule
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import io.kotest.assertions.any
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.*
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class PokemonDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val pokemonEntity = mockk<PokemonEntity>(relaxed = true){
        every { id } returns 1
        every { name } returns "name"
        every { weight } returns 1
        every { height } returns 1
        every { order } returns 1
    }
    private val pokemonItem = mockk<PokemonItem>(relaxed = true){
        every { id } returns 1
        every { name } returns "name"
        every { weight } returns 1
        every { height } returns 1
        every { order } returns 1
    }

    private val repository = mockk<PokemonRepository>()
    private lateinit var viewModel: PokemonDetailsViewModel

    @Before
    fun setUp() = runTest {
        coEvery { repository.getPokemonById(1) } coAnswers {
            delay(1000L)
            Result.Success(pokemonEntity)
        }

        coEvery { repository.getPokemonById(99) } returns Result.Error(IllegalStateException())
        coEvery { pokemonEntity.toPokemonItem() } returns pokemonItem
    }

    @After
    fun tearDown() {
        unmockkAll()
        stopKoin()
    }


    @Test
    fun `test init view model`() = runTest {
        viewModel = PokemonDetailsViewModel(1, repository)
        val result = viewModel.state.value
        result shouldBe PokemonDetailsViewState.Loading
    }

    @Test
    fun `test correct pokemon`() = runTest {
        viewModel = PokemonDetailsViewModel(1, repository)
        advanceTimeBy(1100)
        val result = viewModel.state.value
        result shouldNotBe PokemonDetailsViewState.Loading
        result shouldBe PokemonDetailsViewState.Data(pokemonItem)
    }

    @Test
    fun `test incirrect pokemon`() = runTest {
        viewModel = PokemonDetailsViewModel(99, repository)
        advanceTimeBy(1100)
        val result = viewModel.state.value
        result shouldNotBe PokemonDetailsViewState.Loading
        result shouldNotBe PokemonDetailsViewState.Data(pokemonItem)
        result shouldBe PokemonDetailsViewState.Error("404")
    }
}