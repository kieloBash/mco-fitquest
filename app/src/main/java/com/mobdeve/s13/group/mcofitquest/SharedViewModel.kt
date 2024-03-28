package com.mobdeve.s13.group.mcofitquest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobdeve.s13.group.mcofitquest.models.User

class SharedViewModel : ViewModel() {
    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User> = _userDetails

    fun setUserDetails(user: User) {
        _userDetails.value = user
    }
}
