package com.vovan.pokeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.library.BuildConfig
import com.vovan.pokeapp.R
import com.vovan.pokeapp.presentation.pokedetails.PokemonDetailsFragment
import com.vovan.pokeapp.presentation.pokelist.PokemonListFragment
import com.vovan.pokeapp.presentation.title.TitleFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(), Navigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, TitleFragment())
            .commit()
    }

    override fun openPokemonList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, PokemonListFragment())
            .commit()
    }

    override fun openPokemonDetails(id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, PokemonDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}

interface Navigation{
    fun openPokemonList()
    fun openPokemonDetails(id: Int)
}