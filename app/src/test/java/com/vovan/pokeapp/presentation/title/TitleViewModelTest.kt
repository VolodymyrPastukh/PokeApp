package com.vovan.pokeapp.presentation.title

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class TitleViewModelTest{

    private val viewModel = TitleViewModel()

    @After
    fun cleanUp(){
        stopKoin()
    }

    @Test
    fun `test starting play`(){
        viewModel.startPlay()
        viewModel.isStartPlay.value shouldBe true
    }

    @Test
    fun `test reset play`(){
        viewModel.resetPLay()
        viewModel.isStartPlay.value shouldBe false
    }

}