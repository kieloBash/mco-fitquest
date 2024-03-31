package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityDashboardBinding
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityWorkoutBinding
import com.mobdeve.s13.group.mcofitquest.models.Activity
import com.mobdeve.s13.group.mcofitquest.models.Program
import com.mobdeve.s13.group.mcofitquest.models.User
import com.mobdeve.s13.group.mcofitquest.ui.DashboardFragment
import java.util.ArrayList
import kotlin.random.Random


class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding

    private lateinit var tvImage: ImageView
    private lateinit var tvTarget: TextView
    private lateinit var tvDay: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvMinutes: TextView

    private lateinit var firebaseRefUser : DatabaseReference
    private lateinit var userCurrent : User

    // Use the ViewModel for Sharing User Details
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val data = intent.getParcelableArrayListExtra<Program>("programListKey")
        val programs: ArrayList<Program>? = data

        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvImage = findViewById(R.id.imageView2)

        val imageUrls = arrayOf(
            "https://v2.exercisedb.io/image/wXqlsc9kIGx5ne",
            "https://v2.exercisedb.io/image/3pbblmgUIOylnB",
            "https://v2.exercisedb.io/image/CrBi8dSwHw1mD9",
            "https://v2.exercisedb.io/image/5joMBXB0l-BaHL",
            "https://v2.exercisedb.io/image/xdmcV41ewoMTyF",
            "https://v2.exercisedb.io/image/yHtTDXXO0XnXMc",
            "https://v2.exercisedb.io/image/xmsEvA3z4XBuoI",
            "https://v2.exercisedb.io/image/uFViYVEk7y0zVu",
            "https://v2.exercisedb.io/image/ln0gZbbPXfWxSV",
            "https://v2.exercisedb.io/image/NeqVgcFGJvWYXo",
            "https://v2.exercisedb.io/image/91ppez-6UDKsNU",
            "https://v2.exercisedb.io/image/VtAUC-OKXunYOM"
        )

        // Generate a random number between 0 and 10
        val randomNumber = Random.nextInt(11)

        // Load the image into the ImageView using Glide
        Glide.with(this)
            .load(imageUrls[randomNumber])
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(tvImage)

        tvTarget = findViewById(R.id.type_workout_tv)
        var targetString :String = getIntent().getStringExtra(DashboardFragment.targetKey)!!
        if(targetString!=null){
            tvTarget.text = "${targetString}"
        }

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

            if (programs != null) {
                Log.i("TEXTHELLOdata", data.size.toString())
            }

            intent.putParcelableArrayListExtra("programListKey", data)
            intent.putExtra(DashboardFragment.dayKey, dayString)
            startActivity(intent)
            finish()
        }

        // Initialize the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.workoutactivity_rv_programs)
        // Set the LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        //val user = sharedViewModel.userDetails.value




        Log.i("TEXTHELLOdata", "$data")


        // Initialize your adapter
        val adapter = programs?.let { ProgramsAdapter(it) } // Assuming activities is your data list

        // Set the Adapter
        recyclerView.adapter = adapter

    }

}