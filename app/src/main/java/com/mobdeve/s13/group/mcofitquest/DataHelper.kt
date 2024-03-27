package com.mobdeve.s13.group.mcofitquest

import com.mobdeve.s13.group.mcofitquest.models.Activity

// Do not modify this file except for the package name

class DataHelper {
    companion object {
        fun initializeMyActivitiesData(): ArrayList<Activity> {
            val caloriesArray = intArrayOf(2300,2500,1800,2400,3500,2340,1233,1234)
            val minutesArray = intArrayOf(23,20,21,20,12,32,16,20)

            val data = ArrayList<Activity>()
            for(i in caloriesArray.indices){
                data.add(
                    Activity(
                        i+1,
                        caloriesArray[i],
                        minutesArray[i],
                        0
                    )
                )
            }
            return data;
        }
    }
}