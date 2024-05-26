package com.github.valeriaharumi.challenge.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.ui.components.BottomNav
import com.github.valeriaharumi.challenge.ui.theme.ChallengeTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.github.valeriaharumi.challenge.R
import kotlinx.coroutines.launch
import com.github.valeriaharumi.challenge.data.model.UserDetails
import com.github.valeriaharumi.challenge.ui.board.TinderCard

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, navController : NavController, userId: String) {
//    fun ProfileScreen(profileViewModel: ProfileViewModel, navController : NavController) {

    val currentPage = "profile"
    var userDetails by remember { mutableStateOf<UserDetails?>(null) }

    LaunchedEffect(userId) {
        profileViewModel.viewModelScope.launch {
            userDetails = profileViewModel.getUserDetails(userId)
        }
    }

    ChallengeTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (profile, bottomNav) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(profile) {
                            top.linkTo(parent.top)
                            bottom.linkTo(bottomNav.top)
                            height = Dimension.fillToConstraints
                        }
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Text(text = "Perfil",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorResource(id = R.color.verde_padrao))
                        userDetails?.let {
                            ProfileDetails(it)
                        } ?: run {
                            Text(text = "Gostaríamos de te conhecer melhor")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { navController.navigate("userDetailForm") }) {
                                Text("Adicionar detalhes")
                            }
                        }
                    }
                }

                BottomNav(
                    navController = navController,
                    currentPage = currentPage,
                    modifier = Modifier
                        .constrainAs(bottomNav) {
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }
                )
            }
        }

    }
}

@Composable
fun ProfileDetails(userDetails: UserDetails) {
    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Nome", style = MaterialTheme.typography.titleMedium)
//        Text(text = userDetails.name, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Sobre", style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ))
        Text(text = userDetails.bio, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Educação", style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ))
        Text(text = userDetails.education, style = MaterialTheme.typography.bodyMedium)
        Text(text = userDetails.institution, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Profissional", style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ))
        Text(text = userDetails.company, style = MaterialTheme.typography.bodyMedium)
        Text(text = userDetails.position, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Links", style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ))
        Text(text = "LinkedIn: ${userDetails.linkedin}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "GitHub: ${userDetails.github}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Lattes: ${userDetails.lattes}", style = MaterialTheme.typography.bodyMedium)
    }
}