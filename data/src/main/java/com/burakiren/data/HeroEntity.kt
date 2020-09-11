package com.burakiren.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "extension") val extension: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)