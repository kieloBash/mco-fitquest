package com.mobdeve.s13.group.mcofitquest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.s13.group.mcofitquest.DashboardActivity
import com.mobdeve.s13.group.mcofitquest.MyActivitiesAdapter
import com.mobdeve.s13.group.mcofitquest.R
import com.mobdeve.s13.group.mcofitquest.SharedViewModel
import com.mobdeve.s13.group.mcofitquest.WorkoutActivity
import com.mobdeve.s13.group.mcofitquest.databinding.FragmentDashboardBinding
import com.mobdeve.s13.group.mcofitquest.models.Activity
import com.mobdeve.s13.group.mcofitquest.models.DailyPlan
import com.mobdeve.s13.group.mcofitquest.models.User
import com.mobdeve.s13.group.mcofitquest.models.Workout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var firebaseRefWorkout : DatabaseReference
    private lateinit var firebaseRefDaily : DatabaseReference
    private lateinit var firebaseRefUser : DatabaseReference

    private lateinit var sharedViewModel: SharedViewModel

    private var _binding : FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val workoutList = ArrayList<Workout>()
    private lateinit var userCurrent : User

    /* CONSTANTS */
    private lateinit var STARTINGPLAN : DailyPlan


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        firebaseRefWorkout = FirebaseDatabase.getInstance().getReference("workouts")
        firebaseRefDaily = FirebaseDatabase.getInstance().getReference("dailyplan")
        firebaseRefUser = FirebaseDatabase.getInstance().getReference("user")

        // Add listener for data changes
        firebaseRefWorkout.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the list before adding new data to avoid duplicates
                workoutList.clear()

                // Process the data
                for (workoutSnapshot in dataSnapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    // Do something with the fetched data (e.g., update UI)
                        // Update UI with workout data
                    workout?.let {
                        workoutList.add(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        STARTINGPLAN = DailyPlan(firebaseRefDaily.push().key!!,1,2233,23, workoutList,"Full Body", "Muscle Building", false)

        return binding.root
    }

    private fun updateTextView(userDetails : User) {
        var dayView: TextView = binding.dashboardTvDay
        var goalView: TextView = binding.dashbardTvGoal
        var targetView: TextView = binding.dashbardTvTarget
        var calorieView: TextView = binding.tvCalorieCount
        var minsView: TextView = binding.minsCountTv

        if (userDetails != null) {
            dayView.text = "Day ${userDetails.currentPlan!!.day}"
            goalView.text = "${userDetails.currentPlan!!.goal}"
            targetView.text = "${userDetails.currentPlan!!.target}"

            if(userDetails.currentPlan!!.done == true){
                calorieView.text = "${userDetails.currentPlan!!.totalCalories} kcal."
                minsView.text = "${userDetails.currentPlan!!.totalMinutes} mins."
            }else{
                calorieView.text = "0 kcal."
                minsView.text = "0 mins."
            }

        }
    }

    private fun saveUserDetails(startingPlan : DailyPlan){
        // Retrieve the current user's ID
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            // The user's ID, unique to the Firebase project
            val userId = it.uid

            // Create a User object with the user's ID and other necessary data
            var user = User(userId, emptyList(), startingPlan) // Assuming the User class is defined with these parameters

            // Save the user data to Firebase Realtime Database
            firebaseRefUser.child(userId).setValue(user)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveData(){
        val w = arrayOf(
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "3/4 sit-up",
                gifUrl = "https://v2.exercisedb.io/image/tqvM4EN8Z-P-Dc",
                bodyPart = "waist"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "45° side bend",
                gifUrl = "https://v2.exercisedb.io/image/7xKHzqJF1WA3sp",
                bodyPart = "waist"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "air bike",
                gifUrl = "https://v2.exercisedb.io/image/raZ6kHmoUgwZ0X",
                bodyPart = "waist"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "all fours squad stretch",
                gifUrl = "https://v2.exercisedb.io/image/U--ekfkmbRiabZ",
                bodyPart = "upper legs"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "alternate heel touchers",
                gifUrl = "https://v2.exercisedb.io/image/qiN90U4L17nLbb",
                bodyPart = "waist"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "alternate lateral pulldown",
                gifUrl = "https://v2.exercisedb.io/image/-Ai-tQnIwdpuJI",
                bodyPart = "back"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "ankle circles",
                gifUrl = "https://v2.exercisedb.io/image/Lu3qyQXkRegIHn",
                bodyPart = "lower legs"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "archer pull up",
                gifUrl = "https://v2.exercisedb.io/image/XJRKJfhDnQtFvp",
                bodyPart = "back"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "archer push up",
                gifUrl = "https://v2.exercisedb.io/image/Twm6fBUfhH2-mZ",
                bodyPart = "chest"
            ),
            Workout(
                id = firebaseRefWorkout.push().key!!,
                name = "arm slingers hanging bent knee legs",
                gifUrl = "https://v2.exercisedb.io/image/LDd9PoJTmqHebF",
                bodyPart = "waist"
            )
        )

        for (workout in w) {
            // Save the workout to Firebase
            firebaseRefWorkout.child(firebaseRefWorkout.push().key!!).setValue(workout)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

//        DAILY
        val dailyPlans = arrayOf(
            DailyPlan(firebaseRefDaily.push().key!!,1,2233,23, arrayListOf(w[0],w[3]),"Full Body", "Muscle Building"),
            DailyPlan(firebaseRefDaily.push().key!!,2,3322,28, arrayListOf(w[2],w[4],w[0]), "Upper", "Muscle Building"),
        )
        saveUserDetails(DailyPlan(firebaseRefDaily.push().key!!,1,2233,23, arrayListOf(w[0],w[3]),"Full Body", "Muscle Building", false))
        for (daily in dailyPlans) {
            firebaseRefDaily.child(daily.id!!).setValue(daily)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            updateTextView(user)
        }

        binding.viewAllBtn.setOnClickListener {
            sharedViewModel.userDetails.value?.let { user ->
            } ?: run {
                // This block will be executed if userDetails is null.
                saveUserDetails(STARTINGPLAN)
            }
        }

        firebaseRefUser.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = Firebase.auth.currentUser?.uid
                if(currentUser != null){
                    if(snapshot.exists()){
                        for(userSnap in snapshot.children){
                            val userData = userSnap.getValue(User::class.java)
                            Log.i("Thisuser", userData?.id.toString())
                            Log.i("Thiscurrentuser", "$currentUser")
                            if(userData?.id == currentUser){
                                Log.i("textting", "I AM HERE")
                                userCurrent = userData
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val gradientBox: ConstraintLayout = view.findViewById(R.id.gradient_box)
        gradientBox.setOnClickListener {
            // Use requireActivity() to get the activity context
            val intent = Intent(requireActivity(), WorkoutActivity::class.java)

            Log.i("textting", "I AM HERE2")

            intent.putExtra(targetKey,userCurrent.currentPlan?.target)
            intent.putExtra(dayKey,userCurrent.currentPlan?.day.toString())
            intent.putExtra(caloriesKey,userCurrent.currentPlan?.totalCalories.toString())
            intent.putExtra(minutesKey,userCurrent.currentPlan?.totalMinutes.toString())
            startActivity(intent)
        }

    }

    companion object {
        // These are static keys that you can use for the passing data around.
        const val targetKey : String = "TARGET_KEY"
        const val dayKey : String = "DAY_KEY"
        const val caloriesKey : String = "CALORIES_KEY"
        const val minutesKey : String = "MINUTES_KEY"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}