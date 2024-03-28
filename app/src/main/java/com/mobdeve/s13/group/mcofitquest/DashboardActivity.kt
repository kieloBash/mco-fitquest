package com.mobdeve.s13.group.mcofitquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityDashboardBinding
import com.mobdeve.s13.group.mcofitquest.models.User
import com.mobdeve.s13.group.mcofitquest.ui.ActivitiesFragment
import com.mobdeve.s13.group.mcofitquest.ui.DashboardFragment

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var firebaseRefUser: DatabaseReference
    private lateinit var userDetails: User

    // Use the ViewModel for Sharing User Details
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseRefUser = FirebaseDatabase.getInstance().getReference("user")
        fetchUserDetails()

        replaceFragment(DashboardFragment())

        binding.mainBottomNavigations.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dashboard -> replaceFragment(DashboardFragment())
                R.id.activities -> replaceFragment(ActivitiesFragment())
                else ->{}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame,fragment)
        fragmentTransaction.commit()
    }


    fun fetchUserDetails() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            val userRef = FirebaseDatabase.getInstance().getReference("user").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val u = snapshot.getValue(User::class.java)
                        if (u != null) {
                            userDetails = u
                            // Set the userDetails in the ViewModel
                            sharedViewModel.setUserDetails(userDetails)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

}