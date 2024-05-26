package com.github.valeriaharumi.challenge.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.Groups
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.valeriaharumi.challenge.R
import com.github.valeriaharumi.challenge.ui.theme.ChallengeTheme

@Composable
fun BottomNav(navController: NavController, currentPage: String, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavigationItemData(Icons.Filled.Dashboard, "Board", "board"),
        BottomNavigationItemData(Icons.Filled.Search, "Pesquisa", "search"),
        BottomNavigationItemData(Icons.Filled.Groups, "Mentorias", "mentoring"),
        BottomNavigationItemData(Icons.Filled.Person, "Perfil", "profile")
    )

    ChallengeTheme {
        BottomNavigation(
            modifier = modifier
                .fillMaxWidth(),
                backgroundColor = colorResource(id = R.color.verde_padrao)
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        val isSelected = currentPage == item.route
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) {
                                Color(0xFFEBED98)
                            } else {
                                Color(0xFFF1F1F2)
                            }
                        )
                    },
                    label = { Text(text = item.label,
                        style = if (currentPage == item.route) {
                            MaterialTheme.typography.subtitle1.copy(color = Color(0xFFEBED98))
                        } else {
                            MaterialTheme.typography.subtitle1.copy(color = Color(0xFFF1F1F2))
                        }) },
                    selected = currentPage == item.route,
                    onClick = {
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }
}

data class BottomNavigationItemData(
    val icon: ImageVector,
    val label: String,
    val route: String
)