package com.mobdeve.s13.group.mcofitquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityDashboardBinding
import com.mobdeve.s13.group.mcofitquest.databinding.ActivityProgramDoneBinding
import com.mobdeve.s13.group.mcofitquest.ui.DashboardFragment

class ProgramDoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgramDoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.programDoneBtnDone.setOnClickListener {
            val intent: Intent
            intent = Intent(this,DashboardActivity::class.java)
            intent.putExtra(DashboardFragment.doneKey,true)
            startActivity(intent)
        }
    }
}