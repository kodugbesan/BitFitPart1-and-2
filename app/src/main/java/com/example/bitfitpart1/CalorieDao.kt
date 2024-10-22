package com.example.bitfitpart1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalorieDao {
    @Insert
    suspend fun insert(calorieEntry: CalorieEntry)

    @Query("SELECT * FROM calorieentry ORDER BY date DESC")
    suspend fun getAllEntries(): List<CalorieEntry>
}
