package com.vovan.pokeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.library.BuildConfig
import com.vovan.pokeapp.R
import com.vovan.pokeapp.presentation.pokedetails.PokemonDetailsFragment
import com.vovan.pokeapp.presentation.pokelist.PokemonListFragment
import com.vovan.pokeapp.presentation.title.TitleFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

