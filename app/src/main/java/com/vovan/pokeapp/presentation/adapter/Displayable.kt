package com.vovan.pokeapp.presentation.adapter

import android.view.View
import com.vovan.pokeapp.domain.PokemonStatEntity
import com.vovan.pokeapp.generatePokemonArtUrlFromId
import com.vovan.pokeapp.generatePokemonUrlFromId

interface DisplayableItem

data class PokemonItem(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String = generatePokemonUrlFromId(id),
    val artUrl: String = generatePokemonArtUrlFromId(id),
    val order: Int,
    val isLiked: Boolean = false,
    val generation: Int = 0,
    val types: List<String>? = null,
    val stats: List<PokemonStatItem>? = null,
    val abilities: List<String>? = null
): DisplayableItem

fun PokemonItem.getStoredVisibility() = if(isLiked) View.VISIBLE else View.GONE

data class PokemonStatItem(
    val name: String,
    val base_stat: Int
): DisplayableItem
