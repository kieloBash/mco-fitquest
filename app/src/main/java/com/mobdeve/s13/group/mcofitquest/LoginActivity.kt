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
import com.mobdeve.s13.group.mcofitquest.models.Program
import com.mobdeve.s13.group.mcofitquest.models.Workout
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import android.content.Context

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseRefWorkout : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnGetStarted.setOnClickListener {
            createUserWithEmailAndPassword()
        }
    }

    private fun getCall() {
        val gson = Gson()
        val workoutListType = object : TypeToken<List<Workout>>() {}.type

        firebaseRefWorkout = FirebaseDatabase.getInstance().getReference("workouts")


        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://exercisedb.p.rapidapi.com/exercises?limit=10")
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
                    //Log.i("Workout", workout.toString())
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

                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Create Unsuccessful!", Toast.LENGTH_LONG).show()
                }
            }
    }
}
