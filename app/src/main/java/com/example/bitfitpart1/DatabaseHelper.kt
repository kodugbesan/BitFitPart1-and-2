package com.example.bitfitpart1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "calories.db"
        private const val DATABASE_VERSION = 2 // Incremented version
        private const val TABLE_NAME = "calorieentry"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CALORIES = "calories"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_FOOD = "food"
        private const val COLUMN_PHOTO_PATH = "photo_path" // New column for photo path
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_CALORIES INTEGER, "
                + "$COLUMN_DATE TEXT, "
                + "$COLUMN_FOOD TEXT, "
                + "$COLUMN_PHOTO_PATH TEXT)") // Include photo path in the table
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Updated method to include photo path
    fun insertCalorieEntry(calories: Int, date: String, food: String, photoPath: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_CALORIES, calories)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_FOOD, food)
        contentValues.put(COLUMN_PHOTO_PATH, photoPath) // Store the photo path
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllEntries(): List<CalorieEntry> {
        val entries = mutableListOf<CalorieEntry>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val caloriesIndex = cursor.getColumnIndex(COLUMN_CALORIES)
                val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
                val foodIndex = cursor.getColumnIndex(COLUMN_FOOD)
                val photoPathIndex = cursor.getColumnIndex(COLUMN_PHOTO_PATH) // New index for photo path

                // Ensure column indices are valid before accessing values
                if (idIndex >= 0 && caloriesIndex >= 0 && dateIndex >= 0 && foodIndex >= 0 && photoPathIndex >= 0) {
                    val id = cursor.getLong(idIndex)
                    val calories = cursor.getInt(caloriesIndex)
                    val date = cursor.getString(dateIndex)
                    val food = cursor.getString(foodIndex)
                    val photoPath = cursor.getString(photoPathIndex) // Get the photo path

                    entries.add(CalorieEntry(id, calories, date, food, photoPath)) // Include photo path in the entry
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        return entries
    }
}

