package com.github.valeriaharumi.challenge.ui.mentoring

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.R
import com.github.valeriaharumi.challenge.ui.components.BottomNav

@Composable
fun MentoringScreen(navController : NavController) {
    val currentPage = "mentoring"

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (search, bottomNav, fab) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(search) {
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomNav.top)
                        height = Dimension.fillToConstraints
                    }
                    .padding(16.dp)
            ) {
                Text(text = "Mentorias",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = colorResource(id = R.color.verde_padrao)
                )
            }

            FloatingActionButton(
                onClick = { navController.navigate("mentoringForm") },
                modifier = Modifier
                    .constrainAs(fab) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    },
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    tint = Color.Green,
                    modifier = Modifier.size(48.dp),
                    contentDescription = "Adicionar Mentoria"
                )
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