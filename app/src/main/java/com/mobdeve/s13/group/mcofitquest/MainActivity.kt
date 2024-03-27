package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed(3000){

            val intent: Intent
            if(Firebase.auth.currentUser!=null){
                intent = Intent(this,DashboardActivity::class.java)
            }
            else{
                intent = Intent(this,LoginActivity::class.java)
                intent.putExtra("MODE","SIGNUP")
            }
            startActivity(intent)
        }
    }
}