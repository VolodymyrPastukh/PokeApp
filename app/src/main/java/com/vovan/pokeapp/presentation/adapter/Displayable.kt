package com.vovan.pokeapp.presentation.adapter

interface DisplayableItem

data class PokemonItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val order: Int,
): DisplayableItem
