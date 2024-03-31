package com.mobdeve.s13.group.mcofitquest


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityDashboardBinding
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityProgramBinding
import com.mobdeve.s13.group.mcofitquest.models.Program
import com.mobdeve.s13.group.mcofitquest.ui.SingleProgramFragment

class ProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgramBinding
    var workoutIndex: Int = 0
    //var WORKOUTS = arrayOf("PUSH-UPS","SIT-UPS")
    private lateinit var data: ArrayList<Program>


    override fun onCreate(savedInstanceState: Bundle?) {
        data = intent.getParcelableArrayListExtra<Program>("programListKey")!!

        super.onCreate(savedInstanceState)
        binding = ActivityProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayFragment()


        Log.i("PROGRAMTEST", data?.size.toString())
        binding.programActivityBtnNext.setOnClickListener {
            if(workoutIndex < data!!.size - 1){
                workoutIndex = workoutIndex + 1
                displayFragment()
            } else {
                // Start the new activity
                val intent = Intent(this, ProgramDoneActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.programActivityBtnPrev.setOnClickListener {
            if(workoutIndex > 0){
                workoutIndex = workoutIndex - 1
                displayFragment()
            }
        }

    }

    private fun displayFragment() {
        val singleProgramFragment = SingleProgramFragment()

        val bundle = Bundle().apply {
            putString("WORKOUT_KEY", data?.get(workoutIndex)?.workout)
            putString("REP_KEY", data?.get(workoutIndex)?.reps.toString())
            // Pass the received list to the fragment
            Log.i("PROGRAMHELLO", data?.get(workoutIndex)?.workoutImageUrl.toString())
            putString("PROGRAM_URL_KEY", data.get(workoutIndex).workoutImageUrl)
        }

        singleProgramFragment.arguments = bundle
        replaceFragment(singleProgramFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.program_activity_frame, fragment)
        fragmentTransaction.commit()
    }


}