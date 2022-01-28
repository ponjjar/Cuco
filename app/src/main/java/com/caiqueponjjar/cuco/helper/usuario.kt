package com.caiqueponjjar.cuco.helper;

import android.content.Intent
import com.caiqueponjjar.cuco.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class usuario {
    fun getUser(): FirebaseUser? {

        val user = FirebaseAuth.getInstance().currentUser
        return(user);

    }
    fun getUsername() : String? {
        return(getUser()!!.displayName)
    }
    fun getEmail() : String? {
        return(getUser()?.email)
    }

    fun getUniqueId() : String? {
        return(getUser()?.uid)
    }
}
