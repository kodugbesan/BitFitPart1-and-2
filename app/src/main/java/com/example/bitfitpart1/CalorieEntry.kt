package com.example.bitfitpart1

data class CalorieEntry(
    val id: Long = 0, // Primary key should be Long
    val calories: Int,
    val date: String,
    val food: String,
    val photoPath: String?
)

