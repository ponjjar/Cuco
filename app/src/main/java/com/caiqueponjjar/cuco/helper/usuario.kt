package com.caiqueponjjar.cuco.helper;

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.caiqueponjjar.cuco.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener

import com.google.firebase.database.FirebaseDatabase





class usuario {

    private lateinit var database: DatabaseReference
    fun getUser(): FirebaseUser? {

        val user = FirebaseAuth.getInstance().currentUser
        return(user);

    }fun getAccount(activity: Activity): GoogleSignInAccount? {

        val account = activity?.let { GoogleSignIn.getLastSignedInAccount(it) }
        return(account);

    }
    fun getUsername(activity: Activity) : String? {
        if(getUser()?.displayName != null) {
            return (getUser()?.displayName)
        }
        if(!getAccount(activity)?.displayName.equals(null) ){
            return (getAccount(activity)?.givenName)
        }
        return null
    }
    fun getEmail() : String? {
        return(getUser()?.email)
    }

    fun getUniqueId(activity: Activity) : String? {
        if(getUser()?.email != null) {
            return (getUser()?.email?.replace(".",""))
        }
        if(!getAccount(activity)?.email.equals(null) ){
            return (getAccount(activity)?.email?.replace(".",""))
        }
        return null
    }

    fun commitNewData(activity: Activity, Titulo: String , Subtitulo : String){
        val userId = getUniqueId(activity)
        val username = getUsername(activity)
        database = Firebase.database.reference
        if (userId != null) {
            val DatabaseList = database.child("users").child(userId).push()
            DatabaseList.child("username").setValue(username)
            DatabaseList.child("title").setValue(Titulo)
            DatabaseList.child("subtitle").setValue(Subtitulo)
        }
    }

    fun deleteData(activity: Activity){
        val userId = getUniqueId(activity)
        val ref = FirebaseDatabase.getInstance().reference
        val Query = ref.child("users").child(userId!!).orderByChild("title").equalTo("Apple")

        Query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    appleSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

}
