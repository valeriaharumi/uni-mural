package com.github.valeriaharumi.challenge.ui.profile

import androidx.lifecycle.ViewModel
import com.github.valeriaharumi.challenge.data.model.UserDetails
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {

    suspend fun getUserDetails(uid: String): UserDetails? {
        val database = FirebaseDatabase.getInstance().reference
        return try {
            val snapshot = database.child("users").child(uid).child("userDetails").get().await()
            snapshot.getValue(UserDetails::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}