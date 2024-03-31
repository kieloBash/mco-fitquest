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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mobdeve.s13.group.mcofitquest.models.Program
import kotlin.random.Random

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


        val imageUrls = arrayOf(
            "https://v2.exercisedb.io/image/wXqlsc9kIGx5ne",
            "https://v2.exercisedb.io/image/3pbblmgUIOylnB",
            "https://v2.exercisedb.io/image/CrBi8dSwHw1mD9",
            "https://v2.exercisedb.io/image/5joMBXB0l-BaHL",
            "https://v2.exercisedb.io/image/xdmcV41ewoMTyF",
            "https://v2.exercisedb.io/image/yHtTDXXO0XnXMc",
            "https://v2.exercisedb.io/image/xmsEvA3z4XBuoI",
            "https://v2.exercisedb.io/image/uFViYVEk7y0zVu",
            "https://v2.exercisedb.io/image/ln0gZbbPXfWxSV",
            "https://v2.exercisedb.io/image/NeqVgcFGJvWYXo",
            "https://v2.exercisedb.io/image/91ppez-6UDKsNU",
            "https://v2.exercisedb.io/image/VtAUC-OKXunYOM"
        )

        // Generate a random number between 0 and 10
        val randomNumber = Random.nextInt(11)

        // Load the image into the ImageView using Glide
        Glide.with(this)
            .load(imageUrls[randomNumber])
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
        return view
    }

}