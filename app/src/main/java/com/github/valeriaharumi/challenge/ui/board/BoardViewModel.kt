package com.github.valeriaharumi.challenge.ui.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.valeriaharumi.challenge.data.model.Mentoring
import com.github.valeriaharumi.challenge.data.model.User
import com.github.valeriaharumi.challenge.data.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BoardViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _mentorings = MutableLiveData<List<Mentoring>>()
    val mentorings: LiveData<List<Mentoring>> get() = _mentorings

    private val _users = MutableLiveData<Map<String, User>>()
    val users: LiveData<Map<String, User>> get() = _users

    private val _currentMentoringIndex = MutableStateFlow(0)
    val currentMentoringIndex: StateFlow<Int> = _currentMentoringIndex

    private val database = Firebase.database.reference

    fun loadMentoringsAndUsers() {
        viewModelScope.launch {
            loadMentorings()
        }
    }

    private suspend fun loadMentorings() {
        try {
            Log.d("BoardViewModel", "Starting to load mentorings")
            val snapshot = database.child("mentoring").get().await()
            val mentoringList = snapshot.children.mapNotNull { it.getValue(Mentoring::class.java) }
            Log.d("BoardViewModel", "Loaded mentorings: $mentoringList")
            _mentorings.value = mentoringList
            loadUsersForMentorings(mentoringList)
        } catch (e: Exception) {
            Log.e("BoardViewModel", "Error loading mentorings", e)
        }
    }

    private suspend fun loadUsersForMentorings(mentorings: List<Mentoring>) {
        val loadedUsers = mutableMapOf<String, User>()
        mentorings.forEach { mentoring ->
            val mentorId = mentoring.mentorId
            if (mentorId.isNotEmpty()) {
                try {
                    val userSnapshot = database.child("users").child(mentorId).get().await()
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        loadedUsers[mentorId] = user
                    }
                } catch (e: Exception) {
                    Log.e("BoardViewModel", "Error loading user for mentoring: $mentorId", e)
                }
            }
        }
        _users.postValue(loadedUsers)
    }
}
