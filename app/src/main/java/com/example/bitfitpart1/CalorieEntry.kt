package com.example.bitfitpart1

data class CalorieEntry(
    val id: Long = 0,
    val calories: Int,
    val date: String,
    val food: String,
    val imageUrl: String? = null, // Change this to nullable if not always required
    val photoPath: String? // Keep this as nullable
)


