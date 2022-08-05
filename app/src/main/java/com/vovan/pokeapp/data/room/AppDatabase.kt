package com.vovan.pokeapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vovan.pokeapp.data.room.entity.PokemonDB

@Database(entities = [PokemonDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pokemonDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun getDatabaseInMemory(
            context: Context,
        ): AppDatabase = Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()

    }
}