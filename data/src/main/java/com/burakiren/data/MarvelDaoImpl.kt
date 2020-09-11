package com.burakiren.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burakiren.domain.repository.MarvelDao
import io.reactivex.Single

@Dao
interface MarvelDaoImpl : MarvelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(hero: HeroEntity)

    @Query("SELECT * FROM heroes")
    fun getAllHeroes(): Single<List<HeroEntity>>
}