package com.github.valeriaharumi.challenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.valeriaharumi.challenge.data.repository.AuthRepository
import com.github.valeriaharumi.challenge.ui.board.BoardScreen
import com.github.valeriaharumi.challenge.ui.board.BoardViewModel
import com.github.valeriaharumi.challenge.ui.login.LoginScreen
import com.github.valeriaharumi.challenge.ui.login.LoginViewModel
import com.github.valeriaharumi.challenge.ui.login.TermsScreen
import com.github.valeriaharumi.challenge.ui.mentoring.MentoringFormScreen
import com.github.valeriaharumi.challenge.ui.mentoring.MentoringFormViewModel
import com.github.valeriaharumi.challenge.ui.mentoring.MentoringScreen
import com.github.valeriaharumi.challenge.ui.profile.ProfileScreen
import com.github.valeriaharumi.challenge.ui.profile.ProfileViewModel
import com.github.valeriaharumi.challenge.ui.profile.UserDetailFormScreen
import com.github.valeriaharumi.challenge.ui.profile.UserDetailFormViewModel
import com.github.valeriaharumi.challenge.ui.register.RegisterScreen
import com.github.valeriaharumi.challenge.ui.register.RegisterViewModel
import com.github.valeriaharumi.challenge.ui.search.SearchScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "login") {
        val firebaseAuth = FirebaseAuth.getInstance()
        val authRepository = AuthRepository(firebaseAuth)
        val currentUser = authRepository.getCurrentUser()
        val userId = currentUser!!.uid
        composable("login") {
            LoginScreen(
                LoginViewModel(authRepository),
                navController
            )
        }
        composable("board") {
            BoardScreen(BoardViewModel(authRepository), navController)
        }
        composable("register") {
            RegisterScreen(RegisterViewModel(authRepository), navController)
        }
        composable("profile") {
            ProfileScreen(ProfileViewModel(), navController, userId)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("mentoring") {
            MentoringScreen(navController = navController)
        }
        composable("mentoringForm") {
            MentoringFormScreen(MentoringFormViewModel(), navController = navController, userId)
        }
        composable("terms") {
            TermsScreen(navController)
        }
        composable("userDetailForm") {
            UserDetailFormScreen(UserDetailFormViewModel(), navController, userId)
        }
    }
}