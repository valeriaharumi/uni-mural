package com.github.valeriaharumi.challenge.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.data.model.UserDetails
import com.google.firebase.database.FirebaseDatabase

class UserDetailFormViewModel : ViewModel() {

    private val _educationState = MutableLiveData("")
    val educationState: LiveData<String> get() = _educationState

    private val _institutionState = MutableLiveData("")
    val institutionState: LiveData<String> get() = _institutionState

    private val _companyState = MutableLiveData("")
    val companyState: LiveData<String> get() = _companyState

    private val _positionState = MutableLiveData("")
    val positionState: LiveData<String> get() = _positionState

    private val _bioState = MutableLiveData("")
    val bioState: LiveData<String> get() = _bioState

//    private val _interestsState = MutableLiveData<List<String>>(emptyList())
//    val interestsState: LiveData<List<String>> get() = _interestsState

    private val _linkedinState = MutableLiveData("")
    val linkedinState: LiveData<String> get() = _linkedinState

    private val _githubState = MutableLiveData("")
    val githubState: LiveData<String> get() = _githubState

    private val _lattesState = MutableLiveData("")
    val lattesState: LiveData<String> get() = _lattesState

    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> get() = _isLoadingState

    private val _errorMessageState = MutableLiveData("")
    val errorMessageState: LiveData<String> get() = _errorMessageState

    fun onEducationChanged(newEducation: String) {
        _educationState.value = newEducation
    }

    fun onInstitutionChanged(newInstitution: String) {
        _institutionState.value = newInstitution
    }

    fun onCompanyChanged(newCompany: String) {
        _companyState.value = newCompany
    }

    fun onPositionChanged(newPosition: String) {
        _positionState.value = newPosition
    }

    fun onBioChanged(newBio: String) {
        _bioState.value = newBio
    }

//    fun onInterestsChanged(newInterests: List<String>) {
//        _interestsState.value = newInterests
//    }

    fun onLinkedinChanged(newLinkedin: String) {
        _linkedinState.value = newLinkedin
    }

    fun onGithubChanged(newGithub: String) {
        _githubState.value = newGithub
    }

    fun onLattesChanged(newLattes: String) {
        _lattesState.value = newLattes
    }

    fun saveUserDetails(userId: String, navController: NavController) {
        _isLoadingState.value = true
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        val userDetails = UserDetails(
            education = _educationState.value ?: "",
            institution = _institutionState.value ?: "",
            company = _companyState.value ?: "",
            position = _positionState.value ?: "",
            bio = _bioState.value ?: "",
            //interests = _interestsState.value ?: emptyList(),
            linkedin = _linkedinState.value ?: "",
            github = _githubState.value ?: "",
            lattes = _lattesState.value ?: ""
        )

        val updates = mapOf(
            "userDetails" to userDetails
        )

        usersRef.child(userId).updateChildren(updates)
            .addOnCompleteListener { task ->
                _isLoadingState.value = false
                if (task.isSuccessful) {
                    navController.navigate("profile")
                } else {
                    _errorMessageState.value = "Erro ao salvar os detalhes: ${task.exception?.message}"
                }
            }
    }
    fun fetchUserDetails(userId: String) {
        println(userId)
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users").child(userId).child("userDetails")

        usersRef.get().addOnSuccessListener { dataSnapshot ->
            val userDetails = dataSnapshot.getValue(UserDetails::class.java)
            userDetails?.let {
                _educationState.value = it.education
                _institutionState.value = it.institution
                _companyState.value = it.company
                _positionState.value = it.position
                _bioState.value = it.bio
                _linkedinState.value = it.linkedin
                _githubState.value = it.github
                _lattesState.value = it.lattes
            }
            println(userDetails)
        }.addOnFailureListener { exception ->
            _errorMessageState.value = "Erro ao recuperar os detalhes: ${exception.message}"
        }
    }
}