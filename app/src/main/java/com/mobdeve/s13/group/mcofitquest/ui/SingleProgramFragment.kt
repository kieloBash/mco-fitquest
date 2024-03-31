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

<<<<<<< Updated upstream
=======
        // Generate a random number between 0 and 100
        val randomNumber = Random.nextInt(11)

        // Print the random number
        println("Random Number: $randomNumber")
        if(randomNumber < 1)
            Glide.with(this).load("https://v2.exercisedb.io/image/wXqlsc9kIGx5ne").into(imageView)
        else if(randomNumber < 2)
            Glide.with(this).load("https://v2.exercisedb.io/image/3pbblmgUIOylnB").into(imageView)
        else if(randomNumber < 3)
            Glide.with(this).load("https://v2.exercisedb.io/image/CrBi8dSwHw1mD9").into(imageView)
        else if (randomNumber < 4) {
            Glide.with(this).load("https://v2.exercisedb.io/image/5joMBXB0l-BaHL").into(imageView)
        } else if (randomNumber < 5) {
            Glide.with(this).load("https://v2.exercisedb.io/image/xdmcV41ewoMTyF").into(imageView)
        } else if (randomNumber < 6) {
            Glide.with(this).load("https://v2.exercisedb.io/image/yHtTDXXO0XnXMc").into(imageView)
        } else if (randomNumber < 7) {
            Glide.with(this).load("https://v2.exercisedb.io/image/xmsEvA3z4XBuoI").into(imageView)
        } else if (randomNumber < 8) {
            Glide.with(this).load("https://v2.exercisedb.io/image/uFViYVEk7y0zVu").into(imageView)
        } else if (randomNumber < 9) {
            Glide.with(this).load("https://v2.exercisedb.io/image/ln0gZbbPXfWxSV").into(imageView)
        } else if (randomNumber < 10) {
            Glide.with(this).load("https://v2.exercisedb.io/image/NeqVgcFGJvWYXo").into(imageView)
        } else if (randomNumber < 11) {
            Glide.with(this).load("https://v2.exercisedb.io/image/91ppez-6UDKsNU").into(imageView)
        } else if (randomNumber < 12) {
            Glide.with(this).load("https://v2.exercisedb.io/image/VtAUC-OKXunYOM").into(imageView)
        }
>>>>>>> Stashed changes

        return view
    }

}