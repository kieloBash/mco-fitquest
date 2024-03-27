package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityDashboardBinding
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityProgramBinding
import com.mobdeve.s13.group.mcofitquest.ui.SingleProgramFragment

class ProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgramBinding
    var workoutIndex: Int = 0
    var WORKOUTS = arrayOf("PUSH-UPS","SIT-UPS")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayFragment()


        binding.programActivityBtnNext.setOnClickListener {
            if(workoutIndex < WORKOUTS.size - 1){
                workoutIndex = workoutIndex + 1
                displayFragment()
            } else {
                // Start the new activity
                val intent = Intent(this, ProgramDoneActivity::class.java)
                startActivity(intent)
            }
        }

        binding.programActivityBtnPrev.setOnClickListener {
            if(workoutIndex > 0){
                workoutIndex = workoutIndex - 1
                displayFragment()
            }
        }
    }

    private fun displayFragment(){
        val singleProgramFragment = SingleProgramFragment()
        val bundle = Bundle()
        bundle.putString("WORKOUT_KEY", WORKOUTS[workoutIndex])
        bundle.putString("REP_KEY", "12")
        singleProgramFragment.arguments = bundle
        replaceFragment(singleProgramFragment)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.program_activity_frame,fragment)
        fragmentTransaction.commit()
    }

}