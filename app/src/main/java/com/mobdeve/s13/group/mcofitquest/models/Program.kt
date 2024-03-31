package com.mobdeve.s13.group.mcofitquest.models

import android.os.Parcel
import android.os.Parcelable

data class Program (
    val workout: String,
    val reps: Int,
    val workoutImageId: Int?,
    val workoutImageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workout)
        parcel.writeInt(reps)
        parcel.writeValue(workoutImageId)
        parcel.writeString(workoutImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Program> {
        override fun createFromParcel(parcel: Parcel): Program {
            return Program(parcel)
        }

        override fun newArray(size: Int): Array<Program?> {
            return arrayOfNulls(size)
        }
    }
}
