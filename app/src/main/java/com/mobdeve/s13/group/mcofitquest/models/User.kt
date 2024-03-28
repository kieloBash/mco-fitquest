package com.mobdeve.s13.group.mcofitquest.models

data class User(
    val id : String? = null,
    val history : List<DailyPlan> = emptyList(),
    val currentPlan : DailyPlan? = null,
)
