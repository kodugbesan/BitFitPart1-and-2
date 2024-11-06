package com.example.bitfitpart1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "calories.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "calorieentry"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CALORIES = "calories"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_FOOD = "food"
        private const val COLUMN_PHOTO_PATH = "photo_path"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_CALORIES INTEGER, "
                + "$COLUMN_DATE TEXT, "
                + "$COLUMN_FOOD TEXT, "
                + "$COLUMN_PHOTO_PATH TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertCalorieEntry(calories: Int, date: String, food: String, photoPath: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_CALORIES, calories)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_FOOD, food)
        contentValues.put(COLUMN_PHOTO_PATH, photoPath)

        db.insert(TABLE_NAME, null, contentValues)
        db.close()  // Close the database after use
    }

    fun getAllEntries(): List<CalorieEntry> {
        val entries = mutableListOf<CalorieEntry>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val calories = cursor.getInt(cursor.getColumnIndex(COLUMN_CALORIES))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                val food = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD))
                val photoPath = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH))

                entries.add(CalorieEntry(id, calories, date, food, null, photoPath))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()  // Close the database after use
        return entries
    }

    fun getTotalCalories(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_CALORIES) FROM $TABLE_NAME", null)
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0)  // Get the sum of calories, 0 if null
        }
        cursor.close()
        db.close()  // Close the database after use
        return total
    }

    fun getTotalEntries(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)  // Get the count, 0 if no entries
        }
        cursor.close()
        db.close()  // Close the database after use
        return count
    }
}




