package com.burakiren.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

fun createHeroDao(context: Context): MarvelDaoImpl {
    return Room.databaseBuilder(context, MarvelDatabase::class.java, "heroesdb")
        .build().heroDao()
}

@Database(entities = [HeroEntity::class], version = 1, exportSchema = false)
internal abstract class MarvelDatabase : RoomDatabase() {
    abstract fun heroDao(): MarvelDaoImpl
}