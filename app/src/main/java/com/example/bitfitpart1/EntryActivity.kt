package com.example.bitfitpart1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EntryActivity : AppCompatActivity() {

    private lateinit var editCalories: EditText
    private lateinit var editDate: EditText
    private lateinit var editFood: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonAddPhoto: Button
    private lateinit var imageView: ImageView
    private lateinit var databaseHelper: DatabaseHelper

    private var photoUri: Uri? = null // To store the photo URI

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
        // Launch the image picker
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                photoUri = uri
                imageView.setImageURI(photoUri) // Display the selected photo
                imageView.visibility = ImageView.VISIBLE // Make the image view visible
            }
        }
    }

    private fun saveEntry() {
        val calories = editCalories.text.toString().toIntOrNull() ?: return
        val date = editDate.text.toString()
        val food = editFood.text.toString()
        val photoPath = photoUri?.toString() // Get the photo URI as a string

        // Log the photo path for debugging
        Log.d("EntryActivity", "Saving Entry: Calories: $calories, Date: $date, Food: $food, Photo Path: $photoPath")

        databaseHelper.insertCalorieEntry(calories, date, food, photoPath) // Pass the photo path to the database

        // Optionally, finish the activity after saving
        finish()
    }

    companion object {
        private const val REQUEST_CODE_PHOTO = 1001 // Request code for photo selection
    }
}

