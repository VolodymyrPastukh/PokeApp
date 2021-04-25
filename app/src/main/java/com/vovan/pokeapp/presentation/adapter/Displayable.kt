package com.vovan.pokeapp.presentation.adapter

interface DisplayableItem

data class PokemonItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val attribute: Int = -1,
): DisplayableItem

data class HeaderItem(
    val text: Attribute = Attribute.UNKNOWN
): DisplayableItem{

    enum class Attribute{
        STRENGTH,
        AGILITY,
        INTELLIGENCE,
        UNKNOWN
    }
}