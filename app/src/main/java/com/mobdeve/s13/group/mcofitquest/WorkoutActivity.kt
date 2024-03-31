package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private lateinit var tvTarget: TextView
    private lateinit var tvDay: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvMinutes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("textting", "I AM HERE3")

        tvTarget = findViewById(R.id.type_workout_tv)
        var targetString :String = getIntent().getStringExtra(DashboardFragment.targetKey)!!
        if(targetString!=null){
            tvTarget.text = "${targetString}"
        }
        Log.i("textting", "I AM HERE4")

        tvDay = findViewById(R.id.workoutactivity_tv_day)
        Log.i("textting", "I AM HERE5")

        var dayString :String = getIntent().getStringExtra(DashboardFragment.dayKey)!!
        if(dayString!=null){
            tvDay.text = "Day ${dayString}"
        }
        Log.i("textting", "I AM HERE6")

        tvCalories = findViewById(R.id.workoutactivity_tv_calories)
        var caloriesString :String = getIntent().getStringExtra(DashboardFragment.caloriesKey)!!
        if(caloriesString!=null){
            tvCalories.text = "${caloriesString} kcal."
        }
        Log.i("textting", "I AM HERE7")

        tvMinutes = findViewById(R.id.workoutactivity_tv_minutes)
        var minutesString :String = getIntent().getStringExtra(DashboardFragment.minutesKey)!!
        if(minutesString!=null){
            tvMinutes.text = "${minutesString} mins."
        }
        Log.i("textting", "I AM HERE8")

        binding.workoutactivityBtnStart.setOnClickListener {
            val intent = Intent(this, ProgramActivity::class.java)
            startActivity(intent)
        }
        Log.i("textting", "I AM HERE9")

        // Initialize the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.workoutactivity_rv_programs)
        // Set the LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)
        Log.i("textting", "I AM HERE10")

        val programs: ArrayList<Program> = DataHelper.initializeProgramsData(0)
        // Initialize your adapter
        val adapter = ProgramsAdapter(programs) // Assuming activities is your data list

        // Set the Adapter
        recyclerView.adapter = adapter

    }
}