package com.mobdeve.s13.group.mcofitquest

import com.mobdeve.s13.group.mcofitquest.models.Activity
import com.mobdeve.s13.group.mcofitquest.models.Program

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

        fun initializeProgramsData(programId:Int): ArrayList<Program> {
            val workout1 = arrayOf("Push-ups","Sit-ups","Pull-ups","Jogging")
            val reps1 = intArrayOf(23,20,21,20)

            val workout2 = arrayOf("Leg-ups","Wall Sit","Squats","Jogging")
            val reps2 = intArrayOf(14,23,15,25)

            val workoutArray = arrayOf(workout1,workout2)

            val repsArray = arrayOf(reps1,reps2)

            val data = ArrayList<Program>()
            for(i in workoutArray[programId].indices){
                data.add(
                    Program(
                        workoutArray[programId][i],
                        repsArray[programId][i],
                        0
                    )
                )
            }
            return data;
        }
    }
}