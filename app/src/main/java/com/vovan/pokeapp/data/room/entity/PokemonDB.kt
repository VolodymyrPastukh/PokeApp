package com.vovan.pokeapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonDB(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "image_url") val url: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
)
