package com.mobdeve.s13.group.mcofitquest

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mobdeve.s13.group.mcofitquest.models.Activity
import kotlin.random.Random

class MyActivitiesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    // In our item layout, we need two references -- an ImageView and a TextView. Please note that
    // the itemView is the RecyclerView -- which has context that we can use to find View instances.

    private val img: ImageView = itemView.findViewById(R.id.img)
    private val day: TextView = itemView.findViewById(R.id.tv_workout)
    private val calories: TextView = itemView.findViewById(R.id.tv_reps)
    private val minutes: TextView = itemView.findViewById(R.id.tv_minutes)

    // This is our own method that accepts a Character object and sets our views' info accordingly.
    fun bindData(d: Activity) {
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
        Glide.with(itemView)
            .load(imageUrls[randomNumber])
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(img)

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
