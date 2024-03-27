package com.mobdeve.s13.group.mcofitquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            Firebase.auth.createUserWithEmailAndPassword(binding.emailEtv.text.toString(),
                binding.passwordEtv.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"User Created!!", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Create Unsuccessful!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}