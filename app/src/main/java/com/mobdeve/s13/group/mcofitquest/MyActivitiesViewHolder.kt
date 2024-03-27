package com.mobdeve.s13.group.mcofitquest

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group.mcofitquest.models.Activity

class MyActivitiesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    // In our item layout, we need two references -- an ImageView and a TextView. Please note that
    // the itemView is the RecyclerView -- which has context that we can use to find View instances.

    private val img: ImageView = itemView.findViewById(R.id.img)
    private val day: TextView = itemView.findViewById(R.id.tv_day)
    private val calories: TextView = itemView.findViewById(R.id.tv_calories)
    private val minutes: TextView = itemView.findViewById(R.id.tv_minutes)

    // This is our own method that accepts a Character object and sets our views' info accordingly.
    fun bindData(d: Activity) {
        img.setImageResource(d.workoutImageId!!)
        day.text = "Day ${d.dayId}"
        calories.text = "${d.totalCalories} kcal."
        minutes.text = "${d.totalMinutes} mins."


        // caption
//        if(post.caption!=null){
//            caption.text = post.caption
//            name2.text = post.username
//        }
//        else{
//            caption.visibility = View.GONE
//            name2.visibility = View.GONE
//        }
    }
}
