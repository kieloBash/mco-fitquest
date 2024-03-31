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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseRefDaily : DatabaseReference
    private lateinit var firebaseRefWorkout : DatabaseReference
    private lateinit var firebaseRefUser: DatabaseReference
    private lateinit var userDetails: User

    private lateinit var STARTINGPLAN : DailyPlan

    private val workoutChest = ArrayList<Workout>()
    private val workoutShoulders = ArrayList<Workout>()
    private val workoutBack = ArrayList<Workout>()

    private val userDailyPlan = ArrayList<DailyPlan>()
    // Use the ViewModel for Sharing User Details
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        firebaseRefUser = FirebaseDatabase.getInstance().getReference("user")
        firebaseRefDaily = FirebaseDatabase.getInstance().getReference("dailyplan")

        Log.i("refyser", firebaseRefUser.toString())

        binding.btnGetStarted.setOnClickListener {
            createUserWithEmailAndPassword()
        }

    }

private fun getCall() {
    val gson = Gson()
    val workoutListType = object : TypeToken<List<Workout>>() {}.type

    val client = OkHttpClient()

    val request: Request = Request.Builder()
        .url("https://exercisedb.p.rapidapi.com/exercises?limit=100")
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

            val workouts: List<Workout> = gson.fromJson(myResponse, workoutListType)


            workouts.forEach { workout ->
                // Do something with each workout object

                // Save the workout to Firebase
//                    firebaseRefWorkout.child(workout.id!!).setValue(workout)
//                        .addOnCompleteListener {
//                            Toast.makeText(baseContext, "Data stored successfully", Toast.LENGTH_SHORT).show()
//                        }
//                        .addOnFailureListener { e ->
//                            Toast.makeText(baseContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                        }

                if(workout.bodyPart == "chest" && workoutChest.size < 8){
                    workoutChest.add(workout)
                }
                else if(workout.bodyPart == "shoulders" && workoutShoulders.size < 8){
                    workoutShoulders.add(workout)
                }
                else if(workout.bodyPart == "back" && workoutBack.size < 8){
                    workoutBack.add(workout)
                }

                if(workoutShoulders.size == 8 && workoutChest.size == 8 && workoutBack.size == 8){
                    return@forEach
                }
            }
            Log.i("workoutChest", workoutChest.toString())

            val dayplan = ArrayList<DailyPlan>()
            for(i in 1..30){

                if(i == 1 || i == 4 || i == 7 || i == 10 || i == 13 || i == 16 || i == 19 || i == 22 || i == 25 || i == 28){
                    dayplan.add(DailyPlan("$i", i,2233 + i,60, workoutChest,"Chest", "Muscle Building", false))
                    Log.i("hello$i", workoutChest.toString())
                }
                else if(i == 2 || i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29){
                    dayplan.add(DailyPlan("$i", i,2233 + i,60, workoutShoulders,"Shoulders", "Muscle Building", false))

                    Log.i("hello$i", workoutShoulders.toString())
                }
                else if(i == 3 || i == 6 || i == 9 || i == 12 || i == 15 || i == 18 || i == 21 || i == 24 || i == 27 || i == 30){
                    dayplan.add(DailyPlan("$i", i,2233 + i,60, workoutBack,"Back", "Muscle Building", false))
                    Log.i("hello$i", workoutBack.toString())
                }
            }

            for (daily in dayplan) {
                firebaseRefDaily.child(daily.id!!).setValue(daily)
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

    private fun createUserWithEmailAndPassword() {
        val email = binding.emailEtv.text.toString()
        val password = binding.passwordEtv.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "User Created!!", Toast.LENGTH_LONG).show()

                    getCall()

                    firebaseRefDaily.addValueEventListener(object : ValueEventListener {


                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // Clear the list before adding new data to avoid duplicates
                            userDailyPlan.clear()
                            Log.i("refdaily", firebaseRefDaily.toString())
                            // Process the data
                            for (userDailySnapshot in dataSnapshot.children) {
                                val dayPlan = userDailySnapshot.getValue(DailyPlan::class.java)
                                // Do something with the fetched data (e.g., update UI)
                                // Update UI with workout data
                                dayPlan?.let {
                                    userDailyPlan.add(it)
                                }

                            }
                            Log.i("userdaily", "$userDailyPlan")
                            STARTINGPLAN = userDailyPlan[0]
                            sharedViewModel.userDetails.value?.let { user ->
                            } ?: run {
                                // This block will be executed if userDetails is null.
                                saveUserDetails(STARTINGPLAN)
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })




                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
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
