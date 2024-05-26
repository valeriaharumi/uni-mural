package com.github.valeriaharumi.challenge.ui.login

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TermsScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Sobre os termos",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Lembramos de criar essa página? Siiiiiim",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Sobre as condições",
                fontSize = 3624.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Temos conteúdo pra colocar nela? Naaaaaaao",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Segue assim um belo lorem ipsum",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce non tortor eu justo pharetra tincidunt. Maecenas consectetur quam ut tortor scelerisque, ut ultrices nunc volutpat. Sed nec velit sit amet nisl tempor convallis. Cras posuere libero id turpis commodo, nec efficitur libero interdum. Sed sed velit non felis sagittis pulvinar ac id velit. Nullam non mauris in massa ultrices bibendum. Donec id lobortis purus. Duis euismod malesuada enim nec mattis. Ut nec purus commodo, venenatis elit sit amet, euismod mi. Quisque interdum, velit in vehicula sagittis, massa elit consequat sapien, et gravida lorem nisi vitae tellus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In consectetur nisl sit amet dui maximus efficitur. Ut faucibus bibendum dui, ut facilisis enim efficitur at. Integer dapibus fermentum turpis at posuere.",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Fiz o que pude, não pude muito...",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = {
                    navController.navigate("register")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar")
            }
        }
    }
}