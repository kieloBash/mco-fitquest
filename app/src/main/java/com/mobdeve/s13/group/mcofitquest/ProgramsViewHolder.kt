package com.mobdeve.s13.group.mcofitquest

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group.mcofitquest.models.Activity
import com.mobdeve.s13.group.mcofitquest.models.Program
import com.bumptech.glide.Glide

class ProgramsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    // In our item layout, we need two references -- an ImageView and a TextView. Please note that
    // the itemView is the RecyclerView -- which has context that we can use to find View instances.

    private val img: ImageView = itemView.findViewById(R.id.img)
    private val workout: TextView = itemView.findViewById(R.id.tv_workout)
    private val reps: TextView = itemView.findViewById(R.id.tv_reps)

    // This is our own method that accepts a Character object and sets our views' info accordingly.
    fun bindData(d: Program) {
        img.setImageResource(d.workoutImageId!!)
        workout.text = "${d.workout}"
        reps.text = "${d.reps} reps"

    }
}
