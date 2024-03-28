package com.mobdeve.s13.group.mcofitquest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityLoginBinding
import com.mobdeve.s13.group.mcofitquest.models.Program
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnGetStarted.setOnClickListener {
            createUserWithEmailAndPassword()
        }
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

                    // Perform your OkHttpClient request here
                    val client = OkHttpClient()

                    val mediaType = "application/json".toMediaTypeOrNull()
                    val body = RequestBody.create(mediaType, "{\"query\":\"query MyQuery {\\n  allExercises {\\n    id\\n    name\\n    movement\\n    equipment\\n    type\\n    instructions\\n  }\\n}\"}")
                    val request = Request.Builder()
                        .url("https://advanced-exercise-finder.p.rapidapi.com/")
                        .post(body)
                        .addHeader("x-rapidapi-key", "c4410e7865msha47c5674c4dca27p1decbcjsn187b0f000210")
                        .addHeader("x-rapidapi-host", "advanced-exercise-finder.p.rapidapi.com")
                        .addHeader("Content-Type", "application/json")
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(baseContext, "Request Failed!", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            // Handle response here
                            if (response.isSuccessful) {
                                // Handle successful response
                                val responseBody = response.body?.string()
                                // Process responseBody as needed
                                val gson = Gson()
                                val jsonData = gson.fromJson(responseBody, Program::class.java)

                                println(jsonData)
                                Toast.makeText(baseContext, "Request Successful!!", Toast.LENGTH_LONG).show()
                            } else {
                                // Handle unsuccessful response
                                Toast.makeText(baseContext, "Request Unsuccessful!", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Create Unsuccessful!", Toast.LENGTH_LONG).show()
                }
            }
    }
}
