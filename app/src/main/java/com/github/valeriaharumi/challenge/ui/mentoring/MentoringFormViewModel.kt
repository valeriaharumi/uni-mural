package com.github.valeriaharumi.challenge.ui.mentoring

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.data.model.Mentoring
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MentoringFormViewModel : ViewModel() {
    val titleState = MutableLiveData<String>("")
    val descriptionState = MutableLiveData<String>("")
    val detailsState = MutableLiveData<String>("")
    val areaState = MutableLiveData<String>("")
    val locationState = MutableLiveData<String>("")
    val modalityState = MutableLiveData<String>("")
    val vacanciesState = MutableLiveData<Int>(0)
    val matchMethodState = MutableLiveData<String>("")
    val durationState = MutableLiveData<String>("")
    val frequencyState = MutableLiveData<String>("")
    val periodState = MutableLiveData<String>("")
    val registrationPeriodState = MutableLiveData<String>("")

    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> get() = _isLoadingState

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    fun onTitleChanged(newTitle: String) {
        titleState.value = newTitle
    }

    fun onDescriptionChanged(newDescription: String) {
        descriptionState.value = newDescription
    }

    fun onDetailsChanged(newDetails: String) {
        detailsState.value = newDetails
    }

    fun onAreaChanged(newArea: String) {
        areaState.value = newArea
    }

    fun onLocationChanged(newLocation: String) {
        locationState.value = newLocation
    }

    fun onModalityChanged(newModality: String) {
        modalityState.value = newModality
    }

    fun onVacanciesChanged(newVacancies: Int) {
        vacanciesState.value = newVacancies
    }

    fun onMatchMethodChanged(newMatchMethod: String) {
        matchMethodState.value = newMatchMethod
    }

    fun onDurationChanged(newDuration: String) {
        durationState.value = newDuration
    }

    fun onFrequencyChanged(newFrequency: String) {
        frequencyState.value = newFrequency
    }

    fun onPeriodChanged(newPeriod: String) {
        periodState.value = newPeriod
    }

    fun onRegistrationPeriodChanged(newRegistrationPeriod: String) {
        registrationPeriodState.value = newRegistrationPeriod
    }

    fun saveMentoring(userId: String, navController: NavController) {
        _isLoadingState.value = true
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("mentoring")

        val mentoring = Mentoring(
            mentorId = userId,
            title = titleState.value ?: "",
            description = descriptionState.value ?: "",
            details = detailsState.value ?: "",
            area = areaState.value ?: "",
            location = locationState.value ?: "",
            modality = modalityState.value ?: "",
            vacancies = vacanciesState.value ?: 0,
            matchMethod = matchMethodState.value ?: "",
            duration = durationState.value ?: "",
            frequency = frequencyState.value ?: "",
            period = periodState.value ?: "",
            registrationPeriod = registrationPeriodState.value ?: ""
        )

        usersRef.push().setValue(mentoring)
            .addOnSuccessListener {
                navController.navigate("mentoring")
                viewModelScope.launch {
                    _snackbarMessage.value = "Mentoria salva com sucesso!"
                }
            }
            .addOnFailureListener {
                Log.e("MentoringFormViewModel", "Erro ao salvar a mentoria", it)
                viewModelScope.launch {
                    _snackbarMessage.value = "Erro ao salvar a mentoria"
                }
            }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}