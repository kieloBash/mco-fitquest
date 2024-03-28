package com.mobdeve.s13.group.mcofitquest.models

data class DailyPlan(
    val id : String? = null,
    val day : Int? = null,
    val totalCalories : Int? = null,
    val totalMinutes : Int? = null,
    val workouts: List<Workout> = emptyList(),
    val target : String? = null,
    val goal : String? = null,

    val done : Boolean? = null
)
