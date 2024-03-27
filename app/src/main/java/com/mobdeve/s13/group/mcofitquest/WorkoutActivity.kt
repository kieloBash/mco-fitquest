package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityDashboardBinding
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityWorkoutBinding
import com.mobdeve.s13.group.mcofitquest.models.Activity
import com.mobdeve.s13.group.mcofitquest.models.Program
import com.mobdeve.s13.group.mcofitquest.ui.DashboardFragment

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding

    private lateinit var tvDay: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvMinutes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvDay = findViewById(R.id.workoutactivity_tv_day)
        var dayString :String = getIntent().getStringExtra(DashboardFragment.dayKey)!!
        if(dayString!=null){
            tvDay.text = "Day ${dayString}"
        }

        tvCalories = findViewById(R.id.workoutactivity_tv_calories)
        var caloriesString :String = getIntent().getStringExtra(DashboardFragment.caloriesKey)!!
        if(caloriesString!=null){
            tvCalories.text = "${caloriesString} kcal."
        }

        tvMinutes = findViewById(R.id.workoutactivity_tv_minutes)
        var minutesString :String = getIntent().getStringExtra(DashboardFragment.minutesKey)!!
        if(minutesString!=null){
            tvMinutes.text = "${minutesString} mins."
        }

        binding.workoutactivityBtnStart.setOnClickListener {
            val intent = Intent(this, ProgramActivity::class.java)
            startActivity(intent)
        }

        // Initialize the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.workoutactivity_rv_programs)
        // Set the LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        val programs: ArrayList<Program> = DataHelper.initializeProgramsData(0)
        // Initialize your adapter
        val adapter = ProgramsAdapter(programs) // Assuming activities is your data list

        // Set the Adapter
        recyclerView.adapter = adapter

    }
}