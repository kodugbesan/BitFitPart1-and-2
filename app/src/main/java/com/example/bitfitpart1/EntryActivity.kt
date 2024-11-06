package com.example.bitfitpart1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EntryActivity : AppCompatActivity() {

    private lateinit var editCalories: EditText
    private lateinit var editDate: EditText
    private lateinit var editFood: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonAddPhoto: Button
    private lateinit var imageView: ImageView
    private lateinit var databaseHelper: DatabaseHelper

    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        editCalories = findViewById(R.id.editCalories)
        editDate = findViewById(R.id.editDate)
        editFood = findViewById(R.id.editFood)
        buttonSave = findViewById(R.id.buttonSave)
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto)
        imageView = findViewById(R.id.imageView)

        databaseHelper = DatabaseHelper(this)

        buttonAddPhoto.setOnClickListener {
            selectPhoto()
        }

        buttonSave.setOnClickListener {
            saveEntry()
        }
    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                photoUri = uri
                imageView.setImageURI(photoUri)
                imageView.visibility = ImageView.VISIBLE
            }
        }
    }

    private fun saveEntry() {
        val calories = editCalories.text.toString().toIntOrNull() ?: return
        val date = editDate.text.toString()
        val food = editFood.text.toString()

        if (date.isEmpty() || food.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val photoPath = photoUri?.toString()

        Log.d("EntryActivity", "Saving Entry: Calories: $calories, Date: $date, Food: $food, Photo Path: $photoPath")

        // Insert the entry into the database with the updated method signature
        databaseHelper.insertCalorieEntry(calories, date, food, photoPath)

        Toast.makeText(this, "Entry saved successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        private const val REQUEST_CODE_PHOTO = 1001
    }
}

