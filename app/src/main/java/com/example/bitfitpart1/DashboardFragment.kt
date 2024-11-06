package com.example.bitfitpart1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var textViewTotalCalories: TextView
    private lateinit var textViewTotalEntries: TextView
    private lateinit var textViewCaloriesValue: TextView // Added for calories value
    private lateinit var textViewEntriesValue: TextView // Added for entries value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Initialize the TextViews here
        textViewTotalCalories = view.findViewById(R.id.textViewTotalCalories)
        textViewTotalEntries = view.findViewById(R.id.textViewTotalEntries)
        textViewCaloriesValue = view.findViewById(R.id.textViewCaloriesValue) // Added
        textViewEntriesValue = view.findViewById(R.id.textViewEntriesValue) // Added

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        loadDashboardData()
    }

    private fun loadDashboardData() {
        CoroutineScope(Dispatchers.IO).launch {
            val totalCalories = databaseHelper.getTotalCalories()
            val totalEntries = databaseHelper.getTotalEntries()
            activity?.runOnUiThread {
                // Set the label for Total Calories and the value below it
                textViewTotalCalories.text = "Total Calories"
                textViewCaloriesValue.text = totalCalories.toString() // Set the numeric value

                // Set the label for Total Entries and the value below it
                textViewTotalEntries.text = "Total Entries"
                textViewEntriesValue.text = totalEntries.toString() // Set the numeric value
            }
        }
    }

    // Add this function to update the TextViews directly
    fun updateDashboardValues(calories: Int, entries: Int) {
        textViewTotalCalories.text = "Total Calories"
        textViewCaloriesValue.text = calories.toString() // Update value directly
        textViewTotalEntries.text = "Total Entries"
        textViewEntriesValue.text = entries.toString() // Update value directly
    }
}




