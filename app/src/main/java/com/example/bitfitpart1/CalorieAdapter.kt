package com.example.bitfitpart1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalorieAdapter(private var calorieEntries: List<CalorieEntry>) : RecyclerView.Adapter<CalorieAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val caloriesTextView: TextView = view.findViewById(R.id.tvCalories)
        val dateTextView: TextView = view.findViewById(R.id.tvDate)
        val foodTextView: TextView = view.findViewById(R.id.tvFood) // For the food item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calorie_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = calorieEntries[position]
        holder.caloriesTextView.text = "${entry.calories} calories"
        holder.dateTextView.text = entry.date
        holder.foodTextView.text = entry.food // Set the food item
    }

    override fun getItemCount(): Int {
        return calorieEntries.size
    }

    // New method to update entries and notify the adapter
    fun updateEntries(newEntries: List<CalorieEntry>) {
        calorieEntries = newEntries
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}
