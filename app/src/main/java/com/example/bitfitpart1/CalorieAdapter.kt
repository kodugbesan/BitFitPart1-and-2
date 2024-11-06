package com.example.bitfitpart1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CalorieAdapter(private var calorieEntries: List<CalorieEntry>) : RecyclerView.Adapter<CalorieAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val caloriesTextView: TextView = view.findViewById(R.id.tvCalories)
        val dateTextView: TextView = view.findViewById(R.id.tvDate)
        val foodTextView: TextView = view.findViewById(R.id.tvFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calorie_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = calorieEntries[position]
        holder.caloriesTextView.text = "${entry.calories} calories"
        holder.dateTextView.text = entry.date
        holder.foodTextView.text = entry.food

        Glide.with(holder.itemView.context)
            .load(entry.imageUrl)
    }

    override fun getItemCount(): Int {
        return calorieEntries.size
    }

    fun updateEntries(newEntries: List<CalorieEntry>) {
        calorieEntries = newEntries
        notifyDataSetChanged()
    }
}



