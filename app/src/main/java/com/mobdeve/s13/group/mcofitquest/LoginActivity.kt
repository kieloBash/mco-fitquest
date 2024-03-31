package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityLoginBinding
import com.mobdeve.s13.group.mcofitquest.models.Workout
import okhttp3.*
import java.io.IOException
import androidx.activity.viewModels
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mobdeve.s13.group.mcofitquest.models.DailyPlan
import com.mobdeve.s13.group.mcofitquest.models.User

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseRefDaily : DatabaseReference
    private lateinit var firebaseRefWorkout : DatabaseReference
    private lateinit var firebaseRefUser: DatabaseReference
    private lateinit var userDetails: User

    private lateinit var STARTINGPLAN : DailyPlan

    private val workoutList = ArrayList<Workout>()

    // Use the ViewModel for Sharing User Details
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        firebaseRefUser = FirebaseDatabase.getInstance().getReference("user")

        binding.btnGetStarted.setOnClickListener {
            createUserWithEmailAndPassword()
        }

    }

    private fun getCall(bodyPart: String, limit: String) {
        val gson = Gson()
        val workoutListType = object : TypeToken<List<Workout>>() {}.type

        firebaseRefWorkout = FirebaseDatabase.getInstance().getReference("workouts")

        val urlLink1 = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/"
        val urlLink2 = "?limit=$limit"
        val fullUrl = "$urlLink1$bodyPart$urlLink2"
        val client = OkHttpClient()
        //Log.i("Workoutlink", fullUrl)

        val request: Request = Request.Builder()
            .url(fullUrl)
            .get()
            .addHeader("X-RapidAPI-Key", "c4410e7865msha47c5674c4dca27p1decbcjsn187b0f000210")
            .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
            //.addHeader("X-RapidAPI-Mock-Response", "100")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body!!.string()

                Log.i("Workoutstring", myResponse)

                val workouts: List<Workout> = gson.fromJson(myResponse, workoutListType)
                workouts.forEach { workout ->
                    // Do something with each workout object
                    Log.i("Workout", workout.toString())
                    // Save the workout to Firebase
                    firebaseRefWorkout.child(workout.id!!).setValue(workout)
                        .addOnCompleteListener {
                            Toast.makeText(baseContext, "Data stored successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(baseContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }

            }
        })
    }


    private fun createDayPlan(){

    }
    private fun createUserWithEmailAndPassword() {
        val email = binding.emailEtv.text.toString()
        val password = binding.passwordEtv.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "User Created!!", Toast.LENGTH_LONG).show()

                    getCall("chest", "4")
//                    getCall("lower%20arms", "4")
//                    getCall("back", "2")
//                    getCall("upper%20arms", "2")
//                    getCall("waist", "2")
//                    getCall("shoulder", "4")
//                    getCall("lower%20legs", "2")
//                    getCall("upper%20legs", "2")

//                    firebaseRefDaily = FirebaseDatabase.getInstance().getReference("dailyplan")
//
//                    // Add listener for data changes
//                    firebaseRefWorkout.addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            // Clear the list before adding new data to avoid duplicates
//                            workoutList.clear()
//
//                            // Process the data
//                            for (workoutSnapshot in dataSnapshot.children) {
//                                val workout = workoutSnapshot.getValue(Workout::class.java)
//                                // Do something with the fetched data (e.g., update UI)
//                                // Update UI with workout data
//                                workout?.let {
//                                    workoutList.add(it)
//                                }
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//                    })
//
//                    STARTINGPLAN = DailyPlan(firebaseRefDaily.push().key!!,1,2233,23, workoutList,"Full Body", "Muscle Building", false)
//
//
//                    sharedViewModel.userDetails.value?.let { user ->
//                    } ?: run {
//                        // This block will be executed if userDetails is null.
//                       saveUserDetails(STARTINGPLAN)
//                    }
//
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    startActivity(intent)
//                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Create Unsuccessful!", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(baseContext, "Data stored successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(baseContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}
