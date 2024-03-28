package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.core.os.postDelayed
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityLoginBinding
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityMainBinding
import com.mobdeve.s13.group.mcofitquest.models.User
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler(Looper.getMainLooper()).postDelayed(3000){

            val intent: Intent
            if(Firebase.auth.currentUser!=null){
                Log.i("hellotest", "hello im here")

                intent = Intent(this,DashboardActivity::class.java)
            }
            else{
                intent = Intent(this,LoginActivity::class.java)
                intent.putExtra("MODE","SIGNUP")
            }
            startActivity(intent)
            finish()
        }
    }
}