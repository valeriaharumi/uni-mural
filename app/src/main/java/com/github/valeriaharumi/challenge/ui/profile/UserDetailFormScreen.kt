package com.github.valeriaharumi.challenge.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.data.Options
import com.github.valeriaharumi.challenge.ui.components.Dropdown
import com.github.valeriaharumi.challenge.ui.components.Input

@Composable
fun UserDetailFormScreen(userDetailFormViewModel: UserDetailFormViewModel, navController: NavController, userId: String) {
//    fun UserDetailFormScreen(userDetailFormViewModel: UserDetailFormViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        userDetailFormViewModel.fetchUserDetails(userId)
    }

    val education by userDetailFormViewModel.educationState.observeAsState(initial = "")
    val institution by userDetailFormViewModel.institutionState.observeAsState(initial = "")
    val company by userDetailFormViewModel.companyState.observeAsState(initial = "")
    val position by userDetailFormViewModel.positionState.observeAsState(initial = "")
    val bio by userDetailFormViewModel.bioState.observeAsState(initial = "")
    //val interests by userDetailFormViewModel.interestsState.observeAsState(initial = emptyList())
    val linkedin by userDetailFormViewModel.linkedinState.observeAsState(initial = "")
    val github by userDetailFormViewModel.githubState.observeAsState(initial = "")
    val lattes by userDetailFormViewModel.lattesState.observeAsState(initial = "")
    val isLoading by userDetailFormViewModel.isLoadingState.observeAsState(initial = false)
    val errorMessage by userDetailFormViewModel.errorMessageState.observeAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Dropdown(
            selectedEducation = education,
            onEducationSelected = { userDetailFormViewModel.onEducationChanged(it) },
            options = Options().educationOptions
        )
        Input(
            value = institution,
            placeholder = "",
            label = "Instituição",
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onInstitutionChanged(it)
        }
        Input(
            value = company,
            placeholder = "",
            label = "Empresa",
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onCompanyChanged(it)
        }
        Input(
            value = position,
            placeholder = "",
            label = "Cargo",
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onPositionChanged(it)
        }
        Input(
            value = bio,
            placeholder = "",
            label = "Bio",
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onBioChanged(it)
        }
        Input(
            value = linkedin,
            placeholder = "",
            label = "linkedin",
            modifier = Modifier,
            keyboardType = KeyboardType.Uri,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onLinkedinChanged(it)
        }
        Input(
            value = github,
            placeholder = "",
            label = "Github",
            modifier = Modifier,
            keyboardType = KeyboardType.Uri,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onGithubChanged(it)
        }
        Input(
            value = lattes,
            placeholder = "",
            label = "Lattes",
            modifier = Modifier,
            keyboardType = KeyboardType.Uri,
            visualTransformation = VisualTransformation.None
        ) {
            userDetailFormViewModel.onLattesChanged(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                userDetailFormViewModel.saveUserDetails(userId, navController)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Salvar")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}