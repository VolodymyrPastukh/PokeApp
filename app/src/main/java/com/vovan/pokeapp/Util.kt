package com.vovan.pokeapp

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.vovan.pokeapp.data.dto.PokemonDetailsDTO
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonStatEntity
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.presentation.adapter.PokemonStatItem
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

fun generatePokemonUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

fun generatePokemonArtUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

fun getIdFromUrl(url: String): Int {
    val regex = "\\b[0-9]+".toRegex()
    return regex.find(url)?.value?.toInt() ?: 1
}

fun createRandGradientBackground(): GradientDrawable{
    val gb = GradientDrawable(
        GradientDrawable.Orientation.TL_BR,
        intArrayOf(
            Color.WHITE,
            Color.rgb(
                Random.nextInt(0..255),
                Random.nextInt(0..255),
                Random.nextInt(0..255)
            )
        )
    )
    gb.cornerRadius = 20F
    gb.gradientType = GradientDrawable.RADIAL_GRADIENT
    gb.gradientRadius = 700.0f

    return gb
}

fun PokemonEntity.toPokemonItem(): PokemonItem {
    return PokemonItem(
        id = id,
        name = name,
        height = height,
        weight = weight,
        order = order,
        types = types,
        stats = stats.map { it.toPokemonStatItem() },
        abilities = abilities
    )
}

fun PokemonStatEntity.toPokemonStatItem(): PokemonStatItem{
    return PokemonStatItem(
        name = name,
        base_stat = base_stat
    )
}


fun PokemonDetailsDTO.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        order = order,
        types = types.map { it.type.name },
        stats = stats.map { PokemonStatEntity(it.stat.name, it.base_stat) },
        abilities = abilities.map { it.ability.name }
    )
}

fun PokemonDetailsDTO.toPokemonEntity(generation: Int): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        order = order,
        generation = generation,
        types = types.map { it.type.name },
        stats = stats.map { PokemonStatEntity(it.stat.name, it.base_stat) },
        abilities = abilities.map { it.ability.name }
    )
}