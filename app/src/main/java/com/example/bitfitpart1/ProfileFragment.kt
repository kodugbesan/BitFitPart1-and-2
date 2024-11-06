package com.example.bitfitpart1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var calorieAdapter: CalorieAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var buttonAddEntry: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        calorieAdapter = CalorieAdapter(emptyList())
        recyclerView.adapter = calorieAdapter

        buttonAddEntry = view.findViewById(R.id.buttonAddEntry)
        buttonAddEntry.setOnClickListener {
            val intent = Intent(activity, EntryActivity::class.java)
            startActivity(intent)
        }

        loadCalorieEntries()
    }

    private fun loadCalorieEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            val entries = databaseHelper.getAllEntries()
            activity?.runOnUiThread {
                calorieAdapter.updateEntries(entries)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadCalorieEntries()
    }
}




