package com.mobdeve.s13.group.mcofitquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.mobdeve.s13.group.mcofitquest.models.DailyPlan
import com.mobdeve.s13.group.mcofitquest.models.User
import com.mobdeve.s13.group.mcofitquest.ui.ActivitiesFragment
import com.mobdeve.s13.group.mcofitquest.ui.DashboardFragment

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var firebaseRefUser: DatabaseReference
    private lateinit var firebaseRefDaily: DatabaseReference
    private lateinit var userDetails: User

    private var isDone : Boolean = false

    // Use the ViewModel for Sharing User Details
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseRefUser = FirebaseDatabase.getInstance().getReference("user")
        firebaseRefDaily = FirebaseDatabase.getInstance().getReference("dailyplan")

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

    private fun updateCurrentPlan(){
        Log.i("updateCurrentPlan","UPDATING")
        val nextDay = userDetails.currentPlan?.day?.plus(1) ?: 1
        val newHistory = ArrayList<DailyPlan>()
        for(h in userDetails.history){
            newHistory.add(h)
        }
        newHistory.add(userDetails.currentPlan!!)

        if(nextDay > 30) return;

        firebaseRefDaily.child(nextDay.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()){
                    Log.i("updateCurrentPlan","inside snapshot")
                    val nextPlan = snapshot.getValue(DailyPlan::class.java)
                    if (nextPlan != null) {
                        // Convert nextPlan to a map
                        val newData = mapOf<String, Any>(
                            "currentPlan" to nextPlan,
                            "history" to newHistory
                        )
                        // Assuming you have a reference to the user's node in Firebase
                        // Replace 'userRef' with the actual reference to the user's node
                        // UPDATE
                        val userRef = firebaseRefUser.child(userDetails.id!!)
                        userRef.updateChildren(newData)
                            .addOnSuccessListener {
                                Log.i("updateCurrentPlan", "User updated successfully")

                                userDetails = User(userDetails.id!!, newHistory, nextPlan)
                                sharedViewModel.setUserDetails(userDetails)

                                isDone = false
                            }
                            .addOnFailureListener { e ->
                                Log.e("updateCurrentPlan", "Failed to update user", e)
                            }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError){
            }
        })
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
                            isDone = intent.getBooleanExtra(DashboardFragment.doneKey,false)

                            if(isDone){
                                updateCurrentPlan()
                            }
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