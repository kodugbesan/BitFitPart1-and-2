package com.example.bitfitpart1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var calorieAdapter: CalorieAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        calorieAdapter = CalorieAdapter(emptyList())
        recyclerView.adapter = calorieAdapter

        // Load existing entries from the database
        loadCalorieEntries()

        val buttonAddEntry = findViewById<Button>(R.id.buttonAddEntry)
        buttonAddEntry.setOnClickListener {
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadCalorieEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            val entries = databaseHelper.getAllEntries()
            runOnUiThread {
                calorieAdapter.updateEntries(entries)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadCalorieEntries() // Reload entries when returning to MainActivity
    }
}
