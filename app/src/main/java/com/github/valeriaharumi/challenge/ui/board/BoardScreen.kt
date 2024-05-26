package com.github.valeriaharumi.challenge.ui.board

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.R
import com.github.valeriaharumi.challenge.ui.components.BottomNav
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.valeriaharumi.challenge.data.model.Mentoring
import com.github.valeriaharumi.challenge.data.model.User

@Composable
fun BoardScreen(boardViewModel: BoardViewModel, navController: NavController) {
    val currentPage = "board"

    val whiteWithCustomTransparency = Color.White.copy(alpha = 0.5f)
    val mentorings by boardViewModel.mentorings.observeAsState(emptyList())
    val users by boardViewModel.users.observeAsState(emptyMap())
    var currentMentoringIndex by remember { mutableStateOf(0) }
    val currentMentoring = mentorings.getOrNull(currentMentoringIndex)
    val currentMentorId = currentMentoring?.mentorId
    val currentUser = currentMentorId?.let { users[it] }
    val refreshKey = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        boardViewModel.loadMentoringsAndUsers()
        refreshKey.value++
    }

    DisposableEffect(refreshKey.value) {
        onDispose {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_padrao))
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (card, bottomNav) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = whiteWithCustomTransparency)
                    .constrainAs(card) {
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomNav.top)
                        height = Dimension.fillToConstraints
                    }
                    .padding(16.dp)
            ) {
                currentMentoring?.let { mentoring ->
                    TinderCard(
                        mentoring = mentoring,
                        user = currentUser,
                        onLike = {
                            // Implemente a lógica de Like
                            currentMentoringIndex = (currentMentoringIndex + 1) % mentorings.size
                        },
                        onDislike = {
                            // Implemente a lógica de Dislike
                            currentMentoringIndex = (currentMentoringIndex + 1) % mentorings.size
                        },
                        onViewDetails = {
                            // Implemente a lógica para exibir mais detalhes
                        }
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Desculpe, nenhuma mentoria disponível :(",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
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


@Composable
fun TinderCard(
    mentoring: Mentoring,
    user: User?,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onViewDetails: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        MentoringCardContent(mentoring = mentoring, user = user)
        //MockCardContent()
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            IconButton(onClick = onDislike) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Dislike",
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(onClick = onViewDetails) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "View Details",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onLike) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Like",
                    tint = Color.Green,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Composable
fun MentoringCardContent(
    mentoring: Mentoring,
    user: User?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = mentoring.title ?: "",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Área: ${mentoring.area ?: ""}",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = mentoring.description ?: "",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Mentor: ${user?.name ?: ""}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Instituição: ${user?.userDetails?.institution ?: ""}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Empresa: ${user?.userDetails?.company ?: ""}",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun MockCardContent(
) {
    Spacer(modifier = Modifier.height(48.dp))
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Aprenda a testar suas aplicações",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Text(
            text = "Área: QA",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Nesta mentoria, vou compartilhar técnicas avançadas de qualidade de código, desde princípios de design limpo até automação de testes. Juntos, vamos aprimorar sua capacidade de escrever código robusto, escalável e de fácil manutenção. Entre em contato para começarmos a trabalhar na excelência do seu desenvolvimento!",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Mentor: Fulano da Silva",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Instituição: FIAP",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Empresa: Fiap",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}