package com.vovan.pokeapp.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import timber.log.Timber

@BindingAdapter("pokemonImage")
fun ImageView.setPokemonImageFromUrl(item: PokemonItem?){
    Picasso.get().load(item?.imageUrl).into(this, object : Callback {
        override fun onSuccess() {
            Timber.d("Loaded image")
        }

        override fun onError(e: Exception?) {
            Timber.d("Loaded image exception $e")
        }
    })
}

@BindingAdapter("pokemonName")
fun TextView.setPokemonName(item: PokemonItem?){
    text = item?.name
}