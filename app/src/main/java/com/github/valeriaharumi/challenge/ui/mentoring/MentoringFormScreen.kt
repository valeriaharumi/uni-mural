package com.github.valeriaharumi.challenge.ui.mentoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.R
import com.github.valeriaharumi.challenge.ui.components.BottomNav
import com.github.valeriaharumi.challenge.ui.components.Input
import com.github.valeriaharumi.challenge.ui.components.SnackbarByType
import com.github.valeriaharumi.challenge.ui.components.SnackbarType

@Composable
fun MentoringFormScreen(
    mentoringFormViewModel: MentoringFormViewModel,
    navController: NavController,
    userId: String
) {
    val currentPage = "mentoring"

    val title by mentoringFormViewModel.titleState.observeAsState(initial = "")
    val description by mentoringFormViewModel.descriptionState.observeAsState(initial = "")
    val details by mentoringFormViewModel.detailsState.observeAsState(initial = "")
    val area by mentoringFormViewModel.areaState.observeAsState(initial = "")
    val location by mentoringFormViewModel.locationState.observeAsState(initial = "")
    val modality by mentoringFormViewModel.modalityState.observeAsState(initial = "")
    val vacancies by mentoringFormViewModel.vacanciesState.observeAsState(initial = 0)
    val matchMethod by mentoringFormViewModel.matchMethodState.observeAsState(initial = "")
    val duration by mentoringFormViewModel.durationState.observeAsState(initial = "")
    val frequency by mentoringFormViewModel.frequencyState.observeAsState(initial = "")
    val period by mentoringFormViewModel.periodState.observeAsState(initial = "")
    val registrationPeriod by mentoringFormViewModel.registrationPeriodState.observeAsState(initial = "")
    val isLoading by mentoringFormViewModel.isLoadingState.observeAsState(initial = false)
    val snackbarMessage by mentoringFormViewModel.snackbarMessage.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (form, bottomNav) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(form) {
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomNav.top)
                        height = Dimension.fillToConstraints
                    }
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Cadastrar mentoria",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorResource(id = R.color.verde_padrao)
                        )
                    }
                    item {
                        Input(
                            value = title,
                            placeholder = "",
                            label = "Título",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onTitleChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = description,
                            placeholder = "",
                            label = "Descrição",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onDescriptionChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = details,
                            placeholder = "",
                            label = "Detalhes",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onDetailsChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = area,
                            placeholder = "",
                            label = "Área",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onAreaChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = location,
                            placeholder = "",
                            label = "Localização",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onLocationChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = modality,
                            placeholder = "",
                            label = "Modalidade",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onModalityChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = vacancies.toString(),
                            placeholder = "",
                            label = "Vagas",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Number,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onVacanciesChanged(it.toIntOrNull() ?: 0)
                        }
                    }
                    item {
                        Input(
                            value = matchMethod,
                            placeholder = "",
                            label = "Método de Pareamento",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onMatchMethodChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = duration,
                            placeholder = "",
                            label = "Duração",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onDurationChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = frequency,
                            placeholder = "",
                            label = "Frequência",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onFrequencyChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = period,
                            placeholder = "",
                            label = "Período",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onPeriodChanged(it)
                        }
                    }
                    item {
                        Input(
                            value = registrationPeriod,
                            placeholder = "",
                            label = "Período de Inscrição",
                            modifier = Modifier,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        ) {
                            mentoringFormViewModel.onRegistrationPeriodChanged(it)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                mentoringFormViewModel.saveMentoring(userId, navController)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator()
                            } else {
                                Text("Salvar")
                            }
                        }
                    }
                }
            }

            BottomNav(
                navController = navController,
                currentPage = currentPage,
                modifier = Modifier.constrainAs(bottomNav) {
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )

            snackbarMessage?.let { message ->
                SnackbarByType(
                    message = message,
                    type = SnackbarType.Success,
                    onDismiss = { mentoringFormViewModel.clearSnackbarMessage() }
                )
            }

        }
    }
}