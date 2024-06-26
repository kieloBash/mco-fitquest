package com.mobdeve.s13.group.mcofitquest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
import com.mobdeve.s13.group.mcofitquest.models.Program
import com.mobdeve.s13.group.mcofitquest.models.User
import com.mobdeve.s13.group.mcofitquest.models.Workout
import kotlin.random.Random

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
//        firebaseRefWorkout.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Clear the list before adding new data to avoid duplicates
//                workoutList.clear()
//
//                // Process the data
//                for (workoutSnapshot in dataSnapshot.children) {
//                    val workout = workoutSnapshot.getValue(Workout::class.java)
//                    // Do something with the fetched data (e.g., update UI)
//                        // Update UI with workout data
//                    workout?.let {
//                        workoutList.add(it)
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

        return binding.root
    }

    private fun updateTextView(userDetails : User) {
        var imgView: ImageView = binding.imageView
        var dayView: TextView = binding.dashboardTvDay
        var goalView: TextView = binding.dashbardTvGoal
        var targetView: TextView = binding.dashbardTvTarget
        var calorieView: TextView = binding.tvCalorieCount
        var minsView: TextView = binding.minsCountTv

        if (userDetails != null) {

//            val imageUrls = arrayOf(
//                "https://v2.exercisedb.io/image/wXqlsc9kIGx5ne",
//                "https://v2.exercisedb.io/image/3pbblmgUIOylnB",
//                "https://v2.exercisedb.io/image/CrBi8dSwHw1mD9",
//                "https://v2.exercisedb.io/image/5joMBXB0l-BaHL",
//                "https://v2.exercisedb.io/image/xdmcV41ewoMTyF",
//                "https://v2.exercisedb.io/image/yHtTDXXO0XnXMc",
//                "https://v2.exercisedb.io/image/xmsEvA3z4XBuoI",
//                "https://v2.exercisedb.io/image/uFViYVEk7y0zVu",
//                "https://v2.exercisedb.io/image/ln0gZbbPXfWxSV",
//                "https://v2.exercisedb.io/image/NeqVgcFGJvWYXo",
//                "https://v2.exercisedb.io/image/91ppez-6UDKsNU",
//                "https://v2.exercisedb.io/image/VtAUC-OKXunYOM"
//            )
//
//            // Generate a random number between 0 and 10
//            val randomNumber = Random.nextInt(11)

            // Load the image into the ImageView using Glide
            Glide.with(requireContext())
                .load(userDetails.currentPlan?.workouts?.get(0)?.gifUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgView)
            dayView.text = "Day ${userDetails.currentPlan!!.day}"
            goalView.text = "${userDetails.currentPlan!!.goal}"
            targetView.text = "${userDetails.currentPlan!!.target}"

            var countTotalCalories = 0
            var countTotalMinutes = 0

            for(h in userDetails.history){
                countTotalCalories += h.totalCalories!!
                countTotalMinutes += h.totalMinutes!!
            }
            calorieView.text = "${countTotalCalories} kcal."
            minsView.text = "${countTotalMinutes} mins."
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

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        var userDets: User? = null
        sharedViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            userDets = user
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
                            if(userData?.id == currentUser){
                                Log.i("textting", "I AM HEREsi")
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

            Log.i("textting", "$userDets.currentPlan")

            var reps = 10
            val data = ArrayList<Program>()
            if((userDets!!.currentPlan?.day!! % 7) == 0){
                reps = reps + 2
            }
            for(i in userDets!!.currentPlan?.workouts?.indices!!){
                data.add(
                    Program(
                        userDets!!.currentPlan!!.workouts[i].name!!,
                        reps,
                        0, userDets!!.currentPlan!!.workouts[i].gifUrl
                    )
                )
            }

            val programsList: ArrayList<Program> = data

            intent.putParcelableArrayListExtra("programListKey", programsList)
            intent.putExtra(targetKey,userDets!!.currentPlan?.target)
            intent.putExtra(dayKey,userDets!!.currentPlan?.day.toString())
            intent.putExtra(caloriesKey,userDets!!.currentPlan?.totalCalories.toString())
            intent.putExtra(minutesKey,userDets!!.currentPlan?.totalMinutes.toString())
            startActivity(intent)

        }

    }

    companion object {
        // These are static keys that you can use for the passing data around.

        const val targetKey : String = "TARGET_KEY"
        const val dayKey : String = "DAY_KEY"
        const val caloriesKey : String = "CALORIES_KEY"
        const val minutesKey : String = "MINUTES_KEY"
        const val doneKey : String = "DONE_KEY"
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