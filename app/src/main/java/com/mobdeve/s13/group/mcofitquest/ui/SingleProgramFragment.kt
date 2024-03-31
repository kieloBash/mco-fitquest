package com.mobdeve.s13.group.mcofitquest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mobdeve.s13.group.mcofitquest.R
import android.widget.ImageView
import com.mobdeve.s13.group.mcofitquest.models.Program

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "WORKOUT_KEY"
private const val ARG_PARAM2 = "REP_KEY"
private const val ARG_PARAM3 = "PROGRAM_URL_KEY"
class SingleProgramFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var workoutString: String? = null
    private var repString: String? = null
    private var programList: String? = null

    private lateinit var tvWorkout: TextView
    private lateinit var tvRep: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workoutString = it.getString(ARG_PARAM1)
            repString = it.getString(ARG_PARAM2)
            programList = it.getString(ARG_PARAM3)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_single_program, container, false)
        tvWorkout = view.findViewById(R.id.single_tv_workout)
        workoutString?.let {
            tvWorkout.text = it
        }

        tvRep = view.findViewById(R.id.single_tv_reps)
        repString?.let {
            tvRep.text = "${it} reps"
        }

        val imageView = view.findViewById<ImageView>(R.id.single_img_workout)


//        Glide.with(this).load(programList).into(imageView)
        Glide.with(this).load("https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExd2ptcGxwemduOWgzYTV0M3dlOWN0OGZzemVqZXoxcTEwbTY5aGtsYSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/3HFcLK4IXKoviqvwve/giphy.gif").into(imageView)


        return view
    }

}